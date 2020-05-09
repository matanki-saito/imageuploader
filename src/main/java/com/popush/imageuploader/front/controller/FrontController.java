package com.popush.imageuploader.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontController extends ControllerBase {
    @GetMapping
    public String index() {
        return "front.index.vue";
    }
}
