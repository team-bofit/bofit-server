package org.sopt.bofit.global.messagebroker.handler;

import org.sopt.bofit.global.messagebroker.message.Message;

public interface MessageHandler <T extends Message> {
    void handle(T message, String traceId);

    Class<T> getSupportedType();

    default String getSupportedTypeId() {
        return getSupportedType().getName();
    }

}
