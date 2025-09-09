// File: app/src/main/java/com/example/moviemate/model/LLMRequest.java
package com.example.moviemate.model;

public class LLMRequest {
    // The structure depends heavily on the LLM API.
    // This is a simplified example for a generic API.
    private String prompt;
    // You might also have parameters like 'max_tokens', 'temperature', etc.

    public LLMRequest(String prompt) {
        this.prompt = prompt;
    }
}