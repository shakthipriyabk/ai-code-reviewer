package com.college.ai_code_reviewer.controller;

import com.college.ai_code_reviewer.service.GeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ReviewController {

    @Autowired
    private GeminiService geminiService;

    @PostMapping("/review")
    public ResponseEntity<String> reviewCode(@RequestBody Map<String, String> body) {
        try {
            String code = body.get("code");
            String language = body.getOrDefault("language", "java");
            String result = geminiService.reviewCode(code, language);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
} 
