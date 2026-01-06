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

}
