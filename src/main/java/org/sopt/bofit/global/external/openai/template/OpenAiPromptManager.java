package org.sopt.bofit.global.external.openai.template;

import org.sopt.bofit.domain.insurancereport.entity.InsuranceReport;
import org.sopt.bofit.domain.insurancereport.template.ReportPromptTemplate;
import org.sopt.bofit.domain.user.entity.User;
import org.sopt.bofit.domain.user.entity.UserInfo;
import org.springframework.stereotype.Component;

@Component
public class OpenAiPromptManager {

	public String generateReportRationale(
		User user,
		UserInfo userInfo,
		InsuranceReport report,
		int age
	){
		return ReportPromptTemplate.recommendReasonAndKeywordChip(user, userInfo, report, age);
	}

	public String generateReportSystemMessage(){
		return ReportPromptTemplate.systemMessage();
	}

}
