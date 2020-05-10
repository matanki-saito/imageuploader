package com.popush.imageuploader.front.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.popush.imageuploader.front.model.UploadImageForm;
import com.popush.imageuploader.front.model.UploadImageFormResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
public class FrontApiController extends ControllerBase {
    @PostMapping("/upload")
    public UploadImageFormResponse uploadImage(@RequestBody @Validated UploadImageForm form) {

        return UploadImageFormResponse.builder()
                                      .url("Yukkuri")
                                      .build();
    }
}
