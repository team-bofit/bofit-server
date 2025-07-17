package org.sopt.bofit;

import org.sopt.bofit.global.config.properties.JwtProperties;
import org.sopt.bofit.global.config.properties.KakaoProperties;
import org.sopt.bofit.global.config.properties.OpenAiProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
@EnableConfigurationProperties({JwtProperties.class, KakaoProperties.class, OpenAiProperties.class})
public class BofitApplication {

	public static void main(String[] args) {
		SpringApplication.run(BofitApplication.class, args);
	}

}
