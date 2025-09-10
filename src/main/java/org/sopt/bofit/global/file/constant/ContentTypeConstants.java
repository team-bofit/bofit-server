package org.sopt.bofit.global.file.constant;

import static org.sopt.bofit.global.exception.constant.S3ErrorCode.UNSUPPORTED_MEDIA_TYPE;

import java.util.Arrays;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sopt.bofit.global.exception.customexception.BadRequestException;

@Getter
@AllArgsConstructor
public enum ContentTypeConstants {
    IMAGE("image/")
    ;

    private final String prefix;

    public boolean matches(String contentType) {
        return contentType != null && contentType.startsWith(prefix);
    }

    public static ContentTypeConstants from(String contentType) {
        return Arrays.stream(values())
                .filter(c -> c.matches(contentType))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(UNSUPPORTED_MEDIA_TYPE));
    }
}
