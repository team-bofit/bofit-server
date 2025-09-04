package org.sopt.bofit.global.s3.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.global.s3.constant.ContentTypeConstants;
import org.sopt.bofit.global.s3.dto.response.PresignedUrlResponse;
import org.sopt.bofit.global.s3.util.ContentTypeUtil;
import org.sopt.bofit.global.s3.util.KeyGenerator;
import org.sopt.bofit.global.s3.util.PresignedUrlCreator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final KeyGenerator keyGenerator;

    private final PresignedUrlCreator presignedUrlCreator;

    public PresignedUrlResponse generatePresignedUrls(List<String> contentTypes) {

        List<String> urls = new ArrayList<>(contentTypes.size());

        for (String ct : contentTypes) {
            ContentTypeConstants category = ContentTypeConstants.from(ct);

            ContentTypeUtil.validateContentType(ct);

            String ext = ContentTypeUtil.extensionOf(ct);

            String key = keyGenerator.generate(category, ext);

            String url = presignedUrlCreator.createPutUrl(key, ct);

            urls.add(url);

        }

        return PresignedUrlResponse.of(urls);
    }

}
