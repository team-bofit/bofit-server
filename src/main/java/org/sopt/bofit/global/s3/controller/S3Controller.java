package org.sopt.bofit.global.s3.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.sopt.bofit.global.annotation.CustomExceptionDescription;
import org.sopt.bofit.global.dto.response.BaseResponse;
import org.sopt.bofit.global.s3.dto.request.PresignedUrlRequest;
import org.sopt.bofit.global.s3.dto.response.PresignedUrlResponse;
import org.sopt.bofit.global.s3.service.S3Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.sopt.bofit.global.config.swagger.SwaggerResponseDescription.UPLOAD_IMAGE;

@RestController
@RequiredArgsConstructor
@RequestMapping("images")
public class S3Controller {

    private final S3Service s3Service;

    @Tag(name = "이미지 업로드")
    @Operation(summary = "이미지 업로드 API", description = "mediaType을 통해 PresignedUrl을 발급받습니다.")
    @CustomExceptionDescription(UPLOAD_IMAGE)
    @PostMapping("upload")
    public BaseResponse<PresignedUrlResponse> createdUrls(@RequestBody PresignedUrlRequest req){
        return BaseResponse.ok(s3Service.generatePresignedUrls(req.mediaType()),"presigned URL 발급이 완료되었습니다.");
    }
}
