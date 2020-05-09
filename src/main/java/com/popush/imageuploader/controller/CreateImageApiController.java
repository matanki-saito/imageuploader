package com.popush.imageuploader.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController()
@RequestMapping("/image")
@Validated
public class CreateImageApiController {
    @PostMapping("/aaa")
    public String apiAAA() {
        return "";
    }

    @GetMapping("/bbb")
    public String apiBBB() {
        return "";
    }
}
