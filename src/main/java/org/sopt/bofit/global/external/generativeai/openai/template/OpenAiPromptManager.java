package org.sopt.bofit.global.external.generativeai.openai.template;

import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.insurancereport.service.dto.request.InsuranceCriteria;
import org.sopt.bofit.domain.insurancereport.template.ReportPromptTemplate;
import org.sopt.bofit.domain.user.entity.PersonalInfo;
import org.springframework.stereotype.Component;

@Component
public class OpenAiPromptManager {

	public String generateReportRationale(
        PersonalInfo personalInfo,
		InsuranceCriteria insuranceCriteria,
		InsuranceReport report,
		int age
	){
		return ReportPromptTemplate.recommendReasonAndKeywordChip(personalInfo, insuranceCriteria, report, age);
	}

	public String generateReportSystemMessage(){
		return ReportPromptTemplate.systemMessage();
	}

}
