package com.popush.imageuploader.manager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagerController extends ControllerBase {
    @GetMapping
    public String index() {
        return "manager.index.vue";
    }
}
