package com.example.tryspringsecurity.controller;

import com.example.tryspringsecurity.TrySpringSecurityApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TrySpringSecurityApplication.class)
@ActiveProfiles("test")
public class IndexControllerTests {
}
