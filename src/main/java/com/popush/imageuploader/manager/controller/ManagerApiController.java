package com.popush.imageuploader.manager.controller;

import java.time.ZoneOffset;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.popush.imageuploader.common.service.ImageRepository;
import com.popush.imageuploader.common.service.ImageService;
import com.popush.imageuploader.manager.model.ImageListRecordView;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
public class ManagerApiController extends ControllerBase {
    private final ImageService imageService;
    private final ImageRepository imageRepository;

    @GetMapping("/list")
    public Page<ImageListRecordView> getImageList(Pageable pageable) {
        return imageService.getList(pageable, imageEntityReadonly ->
                ImageListRecordView.builder()
                                   .id(imageEntityReadonly.getImageId())
                                   .timestamp(imageEntityReadonly.getCreatedAt().toEpochSecond(ZoneOffset.UTC))
                                   .active(imageEntityReadonly.isEnable())
                                   .thumbnailUrl(imageRepository.getThumbnailEndPointUri(
                                           imageEntityReadonly.getThumbnailKey()))
                                   .build());
    }
}
