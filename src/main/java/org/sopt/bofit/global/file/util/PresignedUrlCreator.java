package org.sopt.bofit.global.file.util;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.global.config.S3Config;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;

@Component
@RequiredArgsConstructor
public class PresignedUrlCreator {

    private final S3Presigner presigner;

    private final S3Config s3Config;

    private final long ttlMinutes = 10;

    public String createPutUrl(String key, String contentType) {
        PutObjectRequest put = PutObjectRequest.builder()
                .bucket(s3Config.getProperties().s3().bucket())
                .key(key)
                .contentType(contentType)
                .build();

        PresignedPutObjectRequest req = presigner.presignPutObject(b -> b
                .putObjectRequest(put)
                .signatureDuration(Duration.ofMinutes(ttlMinutes)));

        return req.url().toString();
    }
    
}
