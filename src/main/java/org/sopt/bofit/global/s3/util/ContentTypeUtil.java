package org.sopt.bofit.global.s3.util;

import org.sopt.bofit.global.exception.customexception.S3Exception;
import org.sopt.bofit.global.s3.constant.ContentTypeConstants;

import java.util.Locale;
import java.util.Set;

import static org.sopt.bofit.global.exception.constant.S3ErrorCode.UNSUPPORTED_IMAGE_TYPE;
import static org.sopt.bofit.global.exception.constant.S3ErrorCode.UNSUPPORTED_MEDIA_TYPE;

public class ContentTypeUtil {
    private static final Set<String> ALLOWED_IMAGES = Set.of(
            "image/jpeg", "image/jpg", "image/png", "image/webp"
    );

    public static void validateContentType(String contentType){
        var category = ContentTypeConstants.from(contentType);

        switch (category) {
            case IMAGE -> {
                if (!ALLOWED_IMAGES.contains(contentType)) {
                    throw new S3Exception(UNSUPPORTED_IMAGE_TYPE);
                }
            }
            default -> throw new S3Exception(UNSUPPORTED_MEDIA_TYPE);
        }
    }

    public static String extensionOf(String contentType) {
        int idx = contentType.lastIndexOf('/');
        if (idx < 0 || idx == contentType.length() - 1) {
            throw new S3Exception(UNSUPPORTED_MEDIA_TYPE);
        }
        return contentType.substring(idx + 1).toLowerCase(Locale.ROOT);
    }
}
