 
package com.college.ai_code_reviewer.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey;

    public String reviewCode(String code, String language) throws Exception {
        String prompt = "Review this " + language + " code. Point out bugs, improvements, and best practices:\n\n" + code;

        String escapedPrompt = prompt
            .replace("\\", "\\\\")
            .replace("\"", "\\\"")
            .replace("\n", "\\n")
            .replace("\r", "\\r")
            .replace("\t", "\\t");

        String requestBody = "{"
            + "\"contents\": [{\"parts\": [{\"text\": \"" + escapedPrompt + "\"}]}]"
            + "}";

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key=" + apiKey;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
            .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}