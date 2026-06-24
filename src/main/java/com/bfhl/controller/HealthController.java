package com.bfhl.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/health")
@CrossOrigin(origins = "*")
public class HealthController {

    @Value("${bfhl.user-id}")
    private String userId;

    @Value("${bfhl.email}")
    private String email;

    @Value("${bfhl.roll-number}")
    private String rollNumber;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getHealthStatus() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("is_success", true);
        response.put("user_id", userId);
        response.put("email", email);
        response.put("roll_number", rollNumber);
        return ResponseEntity.ok(response);
    }
}
