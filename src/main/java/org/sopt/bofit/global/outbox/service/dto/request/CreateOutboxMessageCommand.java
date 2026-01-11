package org.sopt.bofit.global.outbox.service.dto.request;

import org.sopt.bofit.global.outbox.entity.OutboxMessage;

public record CreateOutboxMessageCommand (
    String traceId,
    String payload,
    String errorMessage,
    Class<?> clazz
){
    public OutboxMessage toEntity(){
        return OutboxMessage.create(traceId, payload, errorMessage, clazz);
    }
}
