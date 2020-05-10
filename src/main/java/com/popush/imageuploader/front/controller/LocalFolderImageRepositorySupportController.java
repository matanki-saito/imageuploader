package com.popush.imageuploader.front.controller;

import java.nio.file.Paths;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.popush.imageuploader.common.service.LocalFolderImageRepository;

import lombok.RequiredArgsConstructor;

@ConditionalOnBean(LocalFolderImageRepository.class)
@RestController
@RequiredArgsConstructor
public class LocalFolderImageRepositorySupportController extends ControllerBase {

    @RequestMapping(value = "/localimage/{key}", produces = "image/png")
    public Resource image(@PathVariable("key") String key) {
        return new FileSystemResource(Paths.get(LocalFolderImageRepository.BASE_DIR, key + ".png"));
    }
}
