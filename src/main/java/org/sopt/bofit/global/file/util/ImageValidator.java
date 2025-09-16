package org.sopt.bofit.global.file.util;

import org.sopt.bofit.global.exception.customexception.BadRequestException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.sopt.bofit.global.exception.constant.PostErrorCode.UNMATCHED_IMAGE;

public class ImageValidator {
    public static void validImageIds(Set<Long> currentImageIds, List<Long> existIds, List<Long> deletedIds){
        Set<Long> expectedIds = new HashSet<>(existIds);
        expectedIds.addAll(deletedIds);

        if(!currentImageIds.equals(expectedIds)){
            throw new BadRequestException(UNMATCHED_IMAGE);
        }
    }
}
