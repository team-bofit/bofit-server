package org.sopt.bofit.global.file.util;

import static org.sopt.bofit.global.exception.constant.S3ErrorCode.UNSUPPORTED_IMAGE_TYPE;
import static org.sopt.bofit.global.exception.constant.S3ErrorCode.UNSUPPORTED_MEDIA_TYPE;

import java.util.Locale;
import java.util.Set;
import org.sopt.bofit.global.exception.customexception.BadRequestException;
import org.sopt.bofit.global.file.constant.ContentTypeConstants;

public class ContentTypeUtil {
    private static final Set<String> ALLOWED_IMAGES = Set.of(
            "image/jpeg", "image/jpg", "image/png", "image/webp"
    );

    public static void validateContentType(String contentType){
        var category = ContentTypeConstants.from(contentType);

        switch (category) {
            case IMAGE -> {
                if (!ALLOWED_IMAGES.contains(contentType)) {
                    throw new BadRequestException(UNSUPPORTED_IMAGE_TYPE);
                }
            }
            default -> throw new BadRequestException(UNSUPPORTED_MEDIA_TYPE);
        }
    }

    public static String extensionOf(String contentType) {
        int idx = contentType.lastIndexOf('/');
        if (idx < 0 || idx == contentType.length() - 1) {
            throw new BadRequestException(UNSUPPORTED_MEDIA_TYPE);
        }
        return contentType.substring(idx + 1).toLowerCase(Locale.ROOT);
    }
}
