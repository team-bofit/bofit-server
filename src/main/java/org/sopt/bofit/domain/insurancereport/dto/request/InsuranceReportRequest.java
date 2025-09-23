package org.sopt.bofit.domain.insurancereport.dto.request;

import static org.sopt.bofit.domain.insurancereport.constant.InsuranceReportConstant.NAME_REGEX;
import static org.sopt.bofit.domain.user.constant.UserInfoConstant.MAX_COVERAGE_PREFERENCES;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import lombok.Builder;
import org.sopt.bofit.domain.insurancereport.annotation.PremiumRange;
import org.sopt.bofit.domain.user.entity.PersonalInfo;
import org.sopt.bofit.domain.user.entity.constant.CoveragePreference;
import org.sopt.bofit.domain.user.entity.constant.DiagnosedDisease;
import org.sopt.bofit.domain.user.entity.constant.Gender;
import org.sopt.bofit.domain.user.entity.constant.Job;
import org.sopt.bofit.domain.user.service.dto.request.PersonalInfoCommand;
import org.sopt.bofit.domain.user.service.dto.request.UserInfoCommand;
import org.springframework.format.annotation.DateTimeFormat;

@PremiumRange
@Builder
public record InsuranceReportRequest(

	@Pattern(message = "실명 입력 불가능한 값이 존재합니다.", regexp = NAME_REGEX)
	@NotBlank(message = "실명은 공백일 수 없습니다.")
	@Schema(description = "실명", example = "김재헌")
	String name,

	@NotNull(message = "생년월일은 필수 항목입니다.")
	@Schema(description = "생년월일", example = "2001-09-04")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate birthDate,

	@NotNull(message = "성별은 필수 항목입니다.")
	@Schema(description = "성별", example = "MALE")
	Gender gender,

	@NotNull(message = "직업은 필수 항목입니다.")
	@Schema(description = "직업", example = "STUDENT")
	Job job,

	@NotNull(message = "결혼 여부는 필수 항목입니다.")
	@Schema(description = "결혼 여부", example = "false")
	boolean isMarried,

	@NotNull(message = "자녀 여부는 필수 항목입니다.")
	@Schema(description = "자녀 여부", example = "false")
	boolean hasChild,

	@NotNull(message = "운전자 여부는 필수 항목입니다.")
	@Schema(description = "운전자 여부", example = "false")
	boolean isDriver,

	@NotNull(message = "본인의 진단 또는 치료 질병 목록은 필수 항목입니다.")
	@Schema(description = "최근 5년 이내에 진단 또는 치료받은 질병 목록", example = "[\"CANCER\", \"HEART\"]")
	List<DiagnosedDisease> diseaseHistory,

	@NotNull(message = "가족 구성원의 진단 또는 치료 질병 목록은 필수 항목입니다.")
	@Schema(description = "가족 구성원이 진단 또는 치료받은 질병 목록", example = "[\"CANCER\", \"RIVER\"]")
	List<DiagnosedDisease> familyHistory,

	@Size(message = "보장 순위 입력은 {max} 개를 초과할 수 없습니다.", max = MAX_COVERAGE_PREFERENCES)
	@NotNull(message = "보장 순위 입력은 필수 항목입니다.")
	@Schema(description = "보장 순위 카테고리 입력", example = "{\n"
		+ "    \"MAJOR_DISEASE\": 1,\n"
		+ "    \"ESSENTIAL_ONLY\": 2\n"
		+ "  }")
	Map<CoveragePreference, Integer> coveragePreferences,

	@NotNull(message = "희망 최소 가격은 필수 항목입니다.")
	@PositiveOrZero(message = "희망 최소 가격은 음수일 수 없습니다.")
	@Schema(description = "희망 최소 가격", example = "70000")
	int minPremium,

	@NotNull(message = "희망 최대 가격은 필수 항목입니다.")
	@Positive(message = "희망 최대 가격은 음수일 수 없습니다.")
	@Schema(description = "희망 최대 가격", example = "150000")
	int maxPremium
) {

	public PersonalInfoCommand toPersonalInfoCommand(){
			return new PersonalInfoCommand(name, gender, birthDate, job, isMarried, isDriver, hasChild);
	}

    public PersonalInfo toPersonalInfo(){
        return PersonalInfo.builder()
            .name(name)
            .gender(gender)
            .birthDate(birthDate)
            .job(job)
            .isMarried(isMarried)
            .isDriver(isDriver)
            .hasChild(hasChild)
            .build();
    }

    public UserInfoCommand toUserInfoCommand(){
        return new UserInfoCommand(
            minPremium,
            maxPremium,
            diseaseHistory,
            familyHistory,
            coveragePreferences
        );
    }

}
