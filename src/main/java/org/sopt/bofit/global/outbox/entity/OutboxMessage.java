package org.sopt.bofit.global.outbox.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.sopt.bofit.global.exception.constant.GlobalErrorCode;
import org.sopt.bofit.global.exception.customexception.ConflictException;
import org.sopt.bofit.global.outbox.entity.constant.MessageStatus;

@Entity
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class OutboxMessage {

    @Id
    private String traceId;

    private String messageClassName;

    @Enumerated(EnumType.STRING)
    private MessageStatus status; // Enum: PENDING, PROCESSING, DONE

    @Column(columnDefinition = "json") // MySQL 5.7+ JSON 타입
    private String payload;

    private int retryCount;

    private LocalDateTime lastAttemptAt;

    private String errorMessage;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Builder
    private OutboxMessage(
        String traceId, String payload, String errorMessage, String messageClassName
    ) {
        this.traceId = traceId;
        this.status = MessageStatus.PENDING;
        this.payload = payload;
        this.errorMessage = errorMessage;
        this.retryCount = 0;
        this.lastAttemptAt = null;
        this.messageClassName = messageClassName;
    }

    public static OutboxMessage create(
        String traceId,
        String payload,
        String errorMessage,
        Class<?> clazz
    ){
        return OutboxMessage.builder()
            .traceId(traceId)
            .payload(payload)
            .errorMessage(errorMessage)
            .messageClassName(clazz.getName())
            .build();
    }

    public void startProcessing() {
        if (this.status == MessageStatus.PROCESSING) {
            throw new ConflictException(GlobalErrorCode.CONFLICT_OUTBOX_STATUS);
        }
        this.status = MessageStatus.PROCESSING;
        this.lastAttemptAt = LocalDateTime.now();
        this.retryCount++;
    }

    public void complete() {
        this.status = MessageStatus.DONE;
    }

    public void failAndRetryLater(String message) {
        this.status = MessageStatus.PENDING;
        this.errorMessage = message;
    }

    public void updateLastAttemptAt(LocalDateTime attemptAt){
        this.lastAttemptAt = attemptAt;
    }
}
