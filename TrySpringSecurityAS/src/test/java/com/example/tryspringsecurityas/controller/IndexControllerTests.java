package com.example.tryspringsecurityas.controller;

import com.example.tryspringsecurityas.TrySpringSecurityApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TrySpringSecurityApplication.class)
@ActiveProfiles("test")
public class IndexControllerTests {
}
