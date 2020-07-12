package com.mikhailkarpov.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/admin")
    public String getAdminResource() {
        return "You've got admin resource";
    }

    @GetMapping("/user")
    public String getUserResource() {
        return "You've got user resource";
    }
}
