package com.example.tryspringsecurity.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public String index(Model model) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        String name = authentication.getName();
        model.addAttribute("name", name);

        return "index";
    }
}
