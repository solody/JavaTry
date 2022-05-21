package com.example.trywebsocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontEndController {
    @GetMapping("/web-socket-page")
    String webSocketPage() {
        return "web-socket-page";
    }
}
