package com.aquainsight.monitoring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AssistantInterfaceController {

    @GetMapping("/assistant")
    public String loadAssistantPage() {
        return "assistant"; // Resolves to assistant.html in the templates folder
    }
}
