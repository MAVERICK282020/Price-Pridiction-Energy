package com.example.energy.controller;

import com.example.energy.dto.ChatRequest;
import com.example.energy.service.ChatbotService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/chatbot")
@CrossOrigin(origins = "*")
public class ChatbotController {
    private final ChatbotService chatbotService;

    public ChatbotController(ChatbotService chatbotService) {
        this.chatbotService = chatbotService;
    }

    @PostMapping("/ask")
    public Map<String, String> ask(@Valid @RequestBody ChatRequest request) {
        return Map.of("reply", chatbotService.reply(request.getMessage()));
    }
}
