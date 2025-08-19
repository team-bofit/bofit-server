package org.sopt.bofit.support;

import org.sopt.bofit.config.TestCacheConfig;
import org.sopt.bofit.global.external.openai.client.OpenAiClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
@Import(TestCacheConfig.class)
public abstract class IntegrationTestSupport {

	@MockBean
	protected OpenAiClient openAiClient;
}
