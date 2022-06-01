package com.example.tryspringboot.controller;

import com.example.tryspringboot.entity.People;
import org.springframework.web.bind.annotation.*;

@RestController
public class HelloController {

    @GetMapping("/")
    public String get() {
        return "hello world";
    }

    @PostMapping("/")
    public People post(@RequestBody People people) {
        return people;
    }
}
