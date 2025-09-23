package org.sopt.bofit.global.external.openai.template;

import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.insurancereport.template.ReportPromptTemplate;
import org.sopt.bofit.domain.user.entity.PersonalInfo;
import org.sopt.bofit.domain.user.entity.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class OpenAiPromptManager {

	public String generateReportRationale(
        PersonalInfo personalInfo,
		UserInfo userInfo,
		InsuranceReport report,
		int age
	){
		return ReportPromptTemplate.recommendReasonAndKeywordChip(personalInfo, userInfo, report, age);
	}

	public String generateReportSystemMessage(){
		return ReportPromptTemplate.systemMessage();
	}

}
