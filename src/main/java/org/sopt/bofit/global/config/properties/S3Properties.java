package org.sopt.bofit.global.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Name;

@ConfigurationProperties(prefix = "cloud.aws")
public record S3Properties(
        Credentials credentials,
        Region region,
        S3 s3
) {
    public record Credentials(String accessKey, String secretKey) {}
    public record Region(@Name("static") String value) {}
    public record S3(String bucket) {}
}