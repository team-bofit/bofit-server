package org.sopt.bofit.global.file.util;

import org.sopt.bofit.global.file.constant.ContentTypeConstants;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class KeyGenerator {

    public String generate(ContentTypeConstants category, String extension) {
        String prefix = category.getPrefix();
        return prefix + UUID.randomUUID() + "." + extension;
    }
}