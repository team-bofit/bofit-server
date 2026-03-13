package org.sopt.bofit.global.file.service;

import lombok.RequiredArgsConstructor;
import org.sopt.bofit.global.file.constant.ContentTypeConstants;
import org.sopt.bofit.global.file.dto.response.PresignedUrlDto;
import org.sopt.bofit.global.file.dto.response.PresignedUrlResponse;
import org.sopt.bofit.global.file.util.ContentTypeUtil;
import org.sopt.bofit.global.file.util.KeyGenerator;
import org.sopt.bofit.global.file.util.PresignedUrlCreator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileService {

    private final KeyGenerator keyGenerator;

    private final PresignedUrlCreator presignedUrlCreator;

    public PresignedUrlResponse generatePresignedUrls(List<String> contentTypes) {

        List<PresignedUrlDto> urls = contentTypes.stream()
                .map(ct -> {
                    ContentTypeConstants category = ContentTypeConstants.from(ct);
                    ContentTypeUtil.validateContentType(ct);
                    String ext = ContentTypeUtil.extensionOf(ct);
                    String key = keyGenerator.generate(category, ext);
                    String presignedUrl = presignedUrlCreator.createPutUrl(key, ct);
                    return new PresignedUrlDto(presignedUrl, key);
                })
                .toList();

        return PresignedUrlResponse.of(urls);
    }

}
