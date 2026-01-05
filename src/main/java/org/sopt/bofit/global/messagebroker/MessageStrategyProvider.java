package org.sopt.bofit.global.messagebroker;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.sopt.bofit.global.exception.constant.GlobalErrorCode;
import org.sopt.bofit.global.exception.customexception.InternalException;
import org.sopt.bofit.global.messagebroker.message.Message;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageStrategyProvider {

    private final Map<Class<? extends Message>, MessageBrokerStrategy> strategyByMessageClass;

    private MessageStrategyProvider(List<MessageBrokerStrategy> messageBrokerStrategies) {
        this.strategyByMessageClass = messageBrokerStrategies.stream()
            .flatMap(strategy ->
                strategy.getSupportedMessageClasses().stream()
                    .map(messageClass -> Map.entry(messageClass, strategy))
            )
            .collect(Collectors.toUnmodifiableMap(
                Entry::getKey,
                Entry::getValue,
                (strategy1, strategy2) -> {
                    log.error("Duplicate message class in {}, {}", strategy1.getClass(),
                        strategy2.getClass());
                    throw new InternalException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
                }
            ));
    }

    public MessageBrokerStrategy getStrategyByMessageClass(Class<? extends Message> messageClass) {
        MessageBrokerStrategy strategy = strategyByMessageClass.get(messageClass);
        if (strategy == null) {
            log.error("No strategy about message: {}", messageClass.getName());
            throw new InternalException(GlobalErrorCode.INTERNAL_SERVER_ERROR);
        }
        return strategy;
    }
}
