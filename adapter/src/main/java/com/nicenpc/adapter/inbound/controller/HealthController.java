package com.nicenpc.adapter.inbound.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HealthController {

    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", Instant.now());
        response.put("service", "nice-npc-springboot-template");
        response.put("version", "1.0.0");
        response.put("company", "Nice NPC");
        response.put("website", "https://nice-npc.com");
        return response;
    }

    @GetMapping("/")
    public Map<String, String> welcome() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Welcome to Nice NPC Spring Boot Template");
        response.put("company", "Nice NPC");
        response.put("website", "https://nice-npc.com");
        response.put("version", "1.0.0");
        return response;
    }
}
