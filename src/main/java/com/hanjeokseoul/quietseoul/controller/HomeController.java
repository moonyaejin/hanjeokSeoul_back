package com.hanjeokseoul.quietseoul.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping("/")
    public String home() {
        return "🚀 Backend 서버 정상 작동중!";
    }
}
