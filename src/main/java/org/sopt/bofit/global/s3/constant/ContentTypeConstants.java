package org.sopt.bofit.global.s3.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.sopt.bofit.global.exception.customexception.S3Exception;

import java.util.Arrays;

import static org.sopt.bofit.global.exception.constant.S3ErrorCode.*;

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
                .orElseThrow(() -> new S3Exception(UNSUPPORTED_MEDIA_TYPE));
    }
}
