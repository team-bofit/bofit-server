package org.sopt.bofit.global.outbox.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.global.exception.constant.GlobalErrorCode;
import org.sopt.bofit.global.exception.customexception.NotFoundException;
import org.sopt.bofit.global.outbox.Repository.OutboxMessageRepository;
import org.sopt.bofit.global.outbox.entity.OutboxMessage;
import org.sopt.bofit.global.outbox.service.dto.request.CreateOutboxMessageCommand;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OutboxMessageService {

    private final OutboxMessageRepository outboxMessageRepository;

    public List<OutboxMessage> getAllNotAttempted(){
        return outboxMessageRepository.findAllByLastAttemptAtIsNull();
    }

    public OutboxMessage create(CreateOutboxMessageCommand command){
        return outboxMessageRepository.save(command.toEntity());
    }

    public OutboxMessage get(String id){
        return outboxMessageRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(GlobalErrorCode.NOT_FOUND_OUTBOX));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markAsProcessing(String traceId) {
        get(traceId).startProcessing();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markAsDone(String traceId) {
        get(traceId).complete();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markAsPending(String traceId, String errorMessage) {
        get(traceId).failAndRetryLater(errorMessage);
    }
    
}
