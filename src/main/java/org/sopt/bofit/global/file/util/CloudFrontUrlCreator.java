package org.sopt.bofit.global.file.util;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.global.config.properties.AWSProperties;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CloudFrontUrlCreator {

    private final AWSProperties awsProperties;

    public String createCloudFrontUrl(String key) {
        String cloudFrontDomain = awsProperties.cloudFront().domain();
        return cloudFrontDomain + "/" + key;
    }

}
