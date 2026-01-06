package org.sopt.bofit.global.messagebroker.sqs.handler.ReportRational;

import lombok.extern.slf4j.Slf4j;
import org.sopt.bofit.domain.insurancereport.service.InsuranceReportService;
import org.sopt.bofit.global.messagebroker.handler.GenerativeAiMessageHandler;
import org.sopt.bofit.global.messagebroker.sqs.handler.SqsMessageHandler;
import org.sopt.bofit.global.messagebroker.sqs.message.CreateReportRationaleMessage;
import org.sopt.bofit.global.outbox.service.OutboxMessageService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
//@RequiredArgsConstructor
public class CreateReportRationaleMessageHandler implements GenerativeAiMessageHandler<CreateReportRationaleMessage>,
    SqsMessageHandler {

    private final InsuranceReportService insuranceReportService;
    private final OutboxMessageService outboxMessageService;

    /**
     * 임시로 Lazy 로딩함.
     * TODO: 추후 cycle 을 끊어내기. fallback 을 generationAiClient 단에서 처리하면 안될 것 같음
     */
    public CreateReportRationaleMessageHandler(
        @Lazy InsuranceReportService insuranceReportService,
        OutboxMessageService outboxMessageService
    ) {
        this.insuranceReportService = insuranceReportService;
        this.outboxMessageService = outboxMessageService;
    }

    @Override
    public void handle(CreateReportRationaleMessage message, String traceId) {
        outboxMessageService.markAsProcessing(traceId);
        try {
            insuranceReportService.updateReportRationale(message.toCommand());
            outboxMessageService.markAsDone(traceId);
        }catch (Exception e){
            outboxMessageService.markAsPending(traceId, e.getMessage());
            throw e;
        }
    }

    @Override
    public Class<CreateReportRationaleMessage> getSupportedType() {
        return CreateReportRationaleMessage.class;
    }

}
