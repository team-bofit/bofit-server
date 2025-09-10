package org.sopt.bofit.global.file.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.global.file.constant.ContentTypeConstants;
import org.sopt.bofit.global.file.dto.response.PresignedUrlResponse;
import org.sopt.bofit.global.file.util.ContentTypeUtil;
import org.sopt.bofit.global.file.util.KeyGenerator;
import org.sopt.bofit.global.file.util.PresignedUrlCreator;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileService {

    private final KeyGenerator keyGenerator;

    private final PresignedUrlCreator presignedUrlCreator;

    public PresignedUrlResponse generatePresignedUrls(List<String> contentTypes) {

        List<String> urls = contentTypes.stream()
                .map(ct -> {
                    ContentTypeConstants category = ContentTypeConstants.from(ct);
                    ContentTypeUtil.validateContentType(ct);
                    String ext = ContentTypeUtil.extensionOf(ct);
                    String key = keyGenerator.generate(category, ext);
                    return presignedUrlCreator.createPutUrl(key, ct);
                })
                .toList();

        return PresignedUrlResponse.of(urls);
    }

}
