package org.sopt.bofit.domain.user.util;

import java.time.LocalDate;
import java.time.Period;

public class UserUtil {

	public static int convertInternationalAge(LocalDate birthDate){
		LocalDate now = LocalDate.now();
		return Period.between(birthDate, now).getYears();
	}
}
