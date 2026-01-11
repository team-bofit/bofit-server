package org.sopt.bofit.global.messagebroker.sqs.strategy;

import static org.sopt.bofit.global.config.ThreadPoolConfig.MESSAGE_CONSUMER_POOL;

import io.awspring.cloud.sqs.annotation.SqsListener;
import io.awspring.cloud.sqs.listener.SqsHeaders;
import io.awspring.cloud.sqs.listener.SqsHeaders.MessageSystemAttributes;
import io.awspring.cloud.sqs.operations.SqsTemplate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.sopt.bofit.global.messagebroker.handler.GenerativeAiMessageHandler;
import org.sopt.bofit.global.messagebroker.message.GenerativeAiMessage;
import org.sopt.bofit.global.messagebroker.message.Message;
import org.sopt.bofit.global.messagebroker.sqs.handler.SqsMessageHandler;
import org.sopt.bofit.global.util.JsonMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.ChangeMessageVisibilityRequest;

@Slf4j
@Component
public class GenerativeAiSqsStrategy <T extends GenerativeAiMessageHandler<? extends GenerativeAiMessage> & SqsMessageHandler>
    extends SqsStrategy {

    protected GenerativeAiSqsStrategy(
        List<T> generativeAiMessageHandlers,
        @Value("${message-broker.sqs.generative-ai}") String queueUrl,
        SqsTemplate sqsTemplate,
        @Qualifier(MESSAGE_CONSUMER_POOL) TaskExecutor messageConsumeExecutor,
        SqsAsyncClient sqsAsyncClient,
        JsonMapper jsonMapper
    ) {
        super(generativeAiMessageHandlers, queueUrl, sqsTemplate,
            messageConsumeExecutor, sqsAsyncClient, jsonMapper);
    }

    @Override
    public void publish(Message message, String traceId) {
        asyncSend(super.getQueueUrl(), message, traceId);
    }

    @Override
    public void publishSync(Message message, String traceId) {
        syncSend(super.getQueueUrl(), message, traceId);
    }

    @Override
    @SqsListener(value = "${message-broker.sqs.generative-ai}", factory = "generativeAiMessageSqsListenerContainerFactory")
    protected void listen(List<org.springframework.messaging.Message<String>> messages) {
        consumeSqsMessages(messages);
    }

    @Override
    protected Void handleProcessingError(org.springframework.messaging.Message<?> message, Throwable e) {
        log.error("[Process Message Error]: {}", message.toString(), e);
        rescheduleMessageWithBackoff(message);
        return null;
    }

    private void rescheduleMessageWithBackoff(org.springframework.messaging.Message<?> message) {
        try {
            String receiptHandle = (String) message.getHeaders().get(SqsHeaders.SQS_RECEIPT_HANDLE_HEADER);

            Object receiveCountHeader = message.getHeaders().get(
                MessageSystemAttributes.SQS_APPROXIMATE_RECEIVE_COUNT); // 재시도 횟수 헤더
            int receiveCount =  receiveCountHeader != null
                ? Integer.parseInt(receiveCountHeader.toString())
                : 1;
            int visibilityTimeout = calculateBackoffSeconds(receiveCount);

            ChangeMessageVisibilityRequest request = ChangeMessageVisibilityRequest.builder()
                .queueUrl(getQueueUrl())
                .receiptHandle(receiptHandle)
                .visibilityTimeout(visibilityTimeout)
                .build();

            getSqsAsyncClient().changeMessageVisibility(request)
                .thenAccept(response -> log.info("Changed visibility to {}s for message retry count {}", visibilityTimeout, receiveCount))
                .exceptionally(ex -> {
                    log.error("Failed to change message visibility", ex);
                    return null;
                });

        } catch (Exception ex) {
            log.error("Error calculating backoff or changing visibility", ex);
        }
    }

    /** 지수백오프 계산
     */
    private int calculateBackoffSeconds(int attempt) {
        int maxVisibility = 43200; // SQS 최대 제한인 12 시간
        double backoff = attempt > 6
            ? maxVisibility
            : Math.pow(3, attempt) * 60;

        return Math.min((int) backoff, maxVisibility);
    }

}
