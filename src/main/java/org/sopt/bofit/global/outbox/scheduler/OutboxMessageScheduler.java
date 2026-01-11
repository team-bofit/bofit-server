package org.sopt.bofit.global.outbox.scheduler;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.bofit.global.messagebroker.MessageBrokerResolver;
import org.sopt.bofit.global.messagebroker.message.Message;
import org.sopt.bofit.global.outbox.entity.OutboxMessage;
import org.sopt.bofit.global.outbox.service.OutboxMessageService;
import org.sopt.bofit.global.util.JsonMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxMessageScheduler {

    private final OutboxMessageService outboxMessageService;
    private final MessageBrokerResolver messageBrokerResolver;
    private final JsonMapper jsonMapper;

    @Scheduled(fixedDelay = 1000 * 60 * 5)
    public void publishOmittedMessages() {
        List<OutboxMessage> omittedMessages = outboxMessageService.getAllNotAttempted();

        for (OutboxMessage outboxMessage : omittedMessages) {
            try {
                Message message = jsonMapper.fromJson(outboxMessage.getMessageClassName(), outboxMessage.getPayload());
                messageBrokerResolver.publishSync(message, outboxMessage.getTraceId());
                outboxMessageService.updateLastAttemptAt(outboxMessage.getTraceId(), LocalDateTime.now());
            } catch (Exception e) {
                log.error("SQS 전송 실패: {}", e.getMessage());
            }
        }
    }
}
