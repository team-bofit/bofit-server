package org.sopt.bofit.global.messagebroker;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sopt.bofit.global.messagebroker.message.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageBrokerResolver {

    private final MessageStrategyProvider messageStrategyProvider;

    public void publish(Message message, String traceId) {
        MessageBrokerStrategy strategy = messageStrategyProvider.getStrategyByMessageClass(
            message.getClass());
        strategy.publish(message, traceId);
    }

    public void publishSync(Message message, String traceId) {
        MessageBrokerStrategy strategy = messageStrategyProvider.getStrategyByMessageClass(
            message.getClass());
        strategy.publishSync(message, traceId);
    }

}
