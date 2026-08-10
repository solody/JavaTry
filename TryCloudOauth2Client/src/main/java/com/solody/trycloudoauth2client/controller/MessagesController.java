package com.solody.trycloudoauth2client.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.oauth2.client.web.ClientAttributes.clientRegistrationId;

@RestController
public class MessagesController {
    private final RestClient restClient;

    public MessagesController(RestClient restClient) {
        this.restClient = restClient;
    }

    @GetMapping("/messages")
    public ResponseEntity<List<String>> messages() {
        String[] messages = this.restClient.get()
                .uri("http://resource-server:8092/messages")
                .attributes(clientRegistrationId("oidc-client"))
                .retrieve()
                .body(String[].class);
        return ResponseEntity.ok(Arrays.asList(messages));
    }

    @GetMapping("/messages-unauth")
    public ResponseEntity<List<String>> messagesUnauth() {
        String[] messages = RestClient.builder().build().get()
                .uri("http://resource-server:8092/messages")
                .retrieve()
                .body(String[].class);
        return ResponseEntity.ok(Arrays.asList(messages));
    }
}
