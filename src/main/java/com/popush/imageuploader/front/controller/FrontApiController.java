package com.popush.imageuploader.front.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@Validated
public class FrontApiController extends ControllerBase {
    @PostMapping("/aaa")
    public String apiAAA() {
        return "";
    }

    @GetMapping("/bbb")
    public String apiBBB() {
        return "";
    }
}
