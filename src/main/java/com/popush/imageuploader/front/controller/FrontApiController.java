package com.popush.imageuploader.front.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.popush.imageuploader.common.service.ImageService;
import com.popush.imageuploader.front.model.UploadImageForm;
import com.popush.imageuploader.front.model.UploadImageFormResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
public class FrontApiController extends ControllerBase {
    private final ImageService imageService;

    @PostMapping("/upload")
    public UploadImageFormResponse uploadImage(@RequestBody @Validated UploadImageForm form) {

        var endPointUri = imageService.saveImage(form.getImage().getBuffer());

        return UploadImageFormResponse.builder()
                                      .url(endPointUri)
                                      .build();
    }
}
