package org.sopt.bofit.global.external.generativeai.openai.client;

import static org.sopt.bofit.domain.insurancereport.constant.InsuranceReportConstant.DEFAULT_RATIONALE_REASONS;
import static org.sopt.bofit.domain.insurancereport.constant.InsuranceReportConstant.DEFAULT_RATIONAL_KEYWORD_CHIPS;
import static org.sopt.bofit.global.constant.ConfigConstant.LLM_RETRY_NAME;
import static org.sopt.bofit.global.exception.constant.GlobalErrorCode.EXTERNAL_SERVER_ERROR;
import static org.sopt.bofit.global.external.generativeai.openai.constant.OpenAiRole.SYSTEM;

import io.github.resilience4j.retry.annotation.Retry;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.log4j.Log4j2;
import org.sopt.bofit.domain.insurancereport.entity.ReportRationale;
import org.sopt.bofit.global.config.properties.OpenAiProperties;
import org.sopt.bofit.global.exception.customexception.InternalException;
import org.sopt.bofit.global.external.generativeai.GenerativeAiClient;
import org.sopt.bofit.global.external.generativeai.openai.dto.request.ChatRequestMessage;
import org.sopt.bofit.global.external.generativeai.openai.dto.request.OpenAiRequest;
import org.sopt.bofit.global.external.generativeai.openai.dto.response.OpenAiResponse;
import org.sopt.bofit.global.external.generativeai.openai.template.OpenAiPromptManager;
import org.sopt.bofit.global.external.generativeai.reportrelational.GenerateReportRationaleRequest;
import org.sopt.bofit.global.messagebroker.MessageBrokerResolver;
import org.sopt.bofit.global.messagebroker.sqs.message.CreateReportRationaleMessage;
import org.sopt.bofit.global.oauth.constant.HttpHeaderConstants;
import org.sopt.bofit.global.outbox.service.OutboxMessageService;
import org.sopt.bofit.global.outbox.service.dto.request.CreateOutboxMessageCommand;
import org.sopt.bofit.global.util.JsonMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;

@Log4j2
@Component
public class OpenAiClient implements GenerativeAiClient {
	public static final String REQUEST_URI = "/chat/completions";

	private final RestClient restClient;
	private final OpenAiProperties properties;
    private final JsonMapper jsonMapper;
    private final OpenAiPromptManager openAiPromptManager;
    private final MessageBrokerResolver messageBrokerResolver;
    private final OutboxMessageService outboxMessageService;

	public OpenAiClient(OpenAiProperties properties, JsonMapper jsonMapper,
        OpenAiPromptManager openAiPromptManager, MessageBrokerResolver messageBrokerResolver,
        OutboxMessageService outboxMessageService) {
		this.properties = properties;
		this.restClient = RestClient.builder()
				.baseUrl(properties.baseUrl())
				.defaultHeader(HttpHeaders.AUTHORIZATION, HttpHeaderConstants.BEARER_PREFIX + properties.secretKey())
				.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
				.build();
        this.jsonMapper = jsonMapper;
        this.openAiPromptManager = openAiPromptManager;
        this.messageBrokerResolver = messageBrokerResolver;
        this.outboxMessageService = outboxMessageService;
    }

    /**
     * API 요청 시 사용함. 최초 호출되어 Fallback 로직 적용
     */
    @Override
    @Retry(name = LLM_RETRY_NAME, fallbackMethod = "generateRationaleFallback")
    public ReportRationale generateReportRationaleForApi(GenerateReportRationaleRequest request) {
        return generateReportRationale(request);
    }

    /**
     * SQS 컨슈머가 사용함. Fallback 없이 예외를 밖으로 던짐
     */
    @Override
    @Retry(name = LLM_RETRY_NAME)
    public ReportRationale generateReportRationaleForMessage(GenerateReportRationaleRequest request) {
        return generateReportRationale(request);
    }

    public ReportRationale generateReportRationale(
        GenerateReportRationaleRequest request
    ){
        try {
            String responseString = generate(
                List.of(
                    new ChatRequestMessage(SYSTEM.getValue(), openAiPromptManager.generateReportSystemMessage()),
                    new ChatRequestMessage(SYSTEM.getValue(), openAiPromptManager.generateReportRationale(
                        request.personalInfo(), request.insuranceCriteria(), request.report(), request.product(), request.age()))
                ));
            return jsonMapper.fromJson(ReportRationale.class, responseString);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // } catch (RuntimeException e) {
            log.info("OpenAI Exception:{}", e.getMessage());
            throw new InternalException(EXTERNAL_SERVER_ERROR);
        }
    }

    /**
     * fallback 메서드, OutboxMessage 생성 및 Sqs 메세지 생성
     */
	public ReportRationale generateRationaleFallback(GenerateReportRationaleRequest request, Throwable t){
        UUID traceId = UUID.randomUUID();
        CreateReportRationaleMessage message = CreateReportRationaleMessage.from(request);
        CreateOutboxMessageCommand command = new CreateOutboxMessageCommand(
            traceId.toString(), jsonMapper.toJson(message), t.getMessage(), message.getClass());
        outboxMessageService.create(command);
        messageBrokerResolver.publish(message, traceId.toString());
		return new ReportRationale(DEFAULT_RATIONALE_REASONS, DEFAULT_RATIONAL_KEYWORD_CHIPS);
	}

    private String generate(List<ChatRequestMessage> messages) {
        OpenAiRequest request = new OpenAiRequest(
            properties.model(),
            messages,
            properties.maxTokens(),
            properties.temperature()
        );

        OpenAiResponse response = restClient.post()
            .uri(REQUEST_URI)
            .body(request)
            .retrieve()
            .body(OpenAiResponse.class);

        return parseContent(Objects.requireNonNull(response));
    }

	private String parseContent(OpenAiResponse response) {
		return response.choices().get(0).message().content().trim();
	}
}
