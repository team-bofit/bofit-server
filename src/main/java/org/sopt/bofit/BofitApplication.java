package org.sopt.bofit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BofitApplication {

	public static void main(String[] args) {
		SpringApplication.run(BofitApplication.class, args);
	}

}
