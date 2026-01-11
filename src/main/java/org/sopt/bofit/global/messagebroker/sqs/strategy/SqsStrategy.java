package org.sopt.bofit.global.messagebroker.sqs.strategy;


import io.awspring.cloud.sqs.listener.SqsHeaders;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.sopt.bofit.global.exception.constant.GlobalErrorCode;
import org.sopt.bofit.global.exception.customexception.InternalException;
import org.sopt.bofit.global.messagebroker.MessageBrokerStrategy;
import org.sopt.bofit.global.messagebroker.handler.MessageHandler;
import org.sopt.bofit.global.messagebroker.message.Message;
import org.sopt.bofit.global.util.JsonMapper;
import org.springframework.core.task.TaskExecutor;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;

@Slf4j
@Getter
public abstract class SqsStrategy extends MessageBrokerStrategy {

    private final String queueUrl;

    private final SqsTemplate sqsTemplate;
    private final TaskExecutor messageConsumeExecutor;
    private final SqsAsyncClient sqsAsyncClient;
    private final JsonMapper jsonMapper;

    private static final String ATTRIBUTE_DATA_TYPE_STRING = "string";
    private static final String ATTRIBUTE_MESSAGE_TYPE = "message";
    private static final String ATTRIBUTE_TRACE_ID = "traceId";
    private static final String ATTRIBUTE_TYPE_ID = "bofitMessageType";

    protected SqsStrategy(
        List<? extends MessageHandler<? extends Message>> messageHandlers,
        String queueUrl,
        SqsTemplate sqsTemplate,
        TaskExecutor messageConsumeExecutor,
        SqsAsyncClient sqsAsyncClient,
        JsonMapper jsonMapper
    ) {
        super(messageHandlers);
        this.queueUrl = queueUrl;
        this.sqsTemplate = sqsTemplate;
        this.messageConsumeExecutor = messageConsumeExecutor;
        this.sqsAsyncClient = sqsAsyncClient;
        this.jsonMapper = jsonMapper;
    }

    abstract void listen(List<org.springframework.messaging.Message<String>> messages);

    protected void asyncSend(String queueName, Message message, String traceId) {
        Map<String, Object> messageAttributes = createMessageAttributes(message, traceId);
        String serializedData = jsonMapper.toJson(message);

        sqsTemplate.sendAsync(to -> to
                .queue(queueName)
                .payload(serializedData)
                .headers(messageAttributes)
            )
            .exceptionally(exception -> {
                String errorMessage = extractExceptionMessage(exception, message);
                log.error("[SqsService send exception] {}", errorMessage, exception);
                return null;
            });
    }

    protected void syncSend(String queueName, Message message, String traceId) {
        Map<String, Object> messageAttributes = createMessageAttributes(message, traceId);
        String serializedData = jsonMapper.toJson(message);

        sqsTemplate.sendAsync(to -> to
                .queue(queueName)
                .payload(serializedData)
                .headers(messageAttributes)
            )
            .join();
    }

    private Map<String, Object> createMessageAttributes(Message message, String traceId) {
        String messageType = message.getClass().getSimpleName();

        return Map.of(
            ATTRIBUTE_MESSAGE_TYPE, messageType,
            ATTRIBUTE_TRACE_ID, traceId,
            ATTRIBUTE_TYPE_ID, getMessageTypeId(message)
        );
    }

    private String getMessageTypeId(Message message) {
        MessageHandler<? extends Message> messageHandler = getHandlerByMessageClass(
            message.getClass());
        return messageHandler.getSupportedTypeId();
    }

    private <T> String extractExceptionMessage(Throwable exception, T data) {
        return String.format("[SQS Exception] Exception Message: %s, Data: %s",
            exception.getMessage(),
            data.toString());
    }

    protected CompletableFuture<Void> processMessageAndDelete(
        org.springframework.messaging.Message<?> message) {
        return CompletableFuture.runAsync(() -> {
                String type = message.getHeaders().get(ATTRIBUTE_TYPE_ID, String.class);
                String traceId = message.getHeaders().get(ATTRIBUTE_TRACE_ID, String.class);
                String payload = message.getPayload().toString();
                handleMessage(type, traceId, payload);
            }, messageConsumeExecutor)
            .thenRun(() -> deleteMessage(message))
            .exceptionally(e -> {
                return handleProcessingError(message, e);
            });
    }

    protected void handleMessage(String typeId, String traceId, String payload) {
        MessageHandler<? extends Message> messageHandler = getHandlerByTypeId(typeId);
        if (messageHandler == null) {
            log.error("Cannot find message handler: {}. Payload: {}", typeId, payload);
            throw new InternalException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }

        processMessage(messageHandler, payload, traceId);
    }

    protected <T extends Message> void processMessage(
        MessageHandler<T> messageHandler,
        String payload,
        String traceId
    ) {
        Class<T> supportedMessageType = messageHandler.getSupportedType();
        T message = jsonMapper.fromJson(supportedMessageType, payload);

        messageHandler.handle(message, traceId);
    }

    public void consumeSqsMessages(
        List<org.springframework.messaging.Message<String>> receivedMessages) {
        List<CompletableFuture<Void>> futures = receivedMessages.stream()
            .map(this::processMessageAndDelete)
            .toList();

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();
    }

    private void deleteMessage(org.springframework.messaging.Message<?> message) {
        String receiptHandle = (String) message.getHeaders()
            .get(SqsHeaders.SQS_RECEIPT_HANDLE_HEADER);

        DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
            .queueUrl(queueUrl)
            .receiptHandle(receiptHandle)
            .build();
        sqsAsyncClient.deleteMessage(deleteMessageRequest);
    }

    protected Void handleProcessingError(org.springframework.messaging.Message<?> message, Throwable e) {
        log.error("[Process Message Error]: {}", message.toString(), e);
        return null;
    }
}
