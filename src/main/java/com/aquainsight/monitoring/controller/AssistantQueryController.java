package com.aquainsight.monitoring.controller;

import com.aquainsight.monitoring.model.WaterSample;
import com.aquainsight.monitoring.service.QualityMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assistant")
public class AssistantQueryController {

    @Autowired
    private QualityMetricsService qualityMetricsService;

    @GetMapping
    public ResponseEntity<String> processUserQuery(@RequestParam String query) {
        String response;
        String normalizedQuery = query.toLowerCase();

        // Greeting handler
        if (normalizedQuery.equals("hello") || normalizedQuery.equals("hi") || normalizedQuery.equals("hey")) {
            return ResponseEntity.ok("👋 Hello there! How can I help you with water metrics?");
        }

        String locationCode = extractLocationCode(normalizedQuery);
        if (locationCode.isEmpty()) {
            return ResponseEntity.ok("📍 Kindly specify the location code in your query.");
        }

        List<WaterSample> dataRecords = qualityMetricsService.fetchSamplesByLocation(locationCode);

        if (dataRecords.isEmpty()) {
            return ResponseEntity.ok("⚠️ No water data available for the specified location.");
        }

        WaterSample latestEntry = dataRecords.get(dataRecords.size() - 1);
        Map<String, Object> metrics = latestEntry.getQualityMetrics();

        if (metrics == null || metrics.isEmpty()) {
            return ResponseEntity.ok("⚠️ No parameters found in the latest water data.");
        }

        // Respond based on detected parameter
        if (normalizedQuery.contains("ph")) {
            response = String.format("📊 pH level at %s: %s", locationCode, metrics.get("pH"));
        } else if (normalizedQuery.contains("chlorine")) {
            response = String.format("📊 Chlorine concentration at %s: %s", locationCode, metrics.get("chlorine"));
        } else if (normalizedQuery.contains("colour")) {
            response = String.format("📊 Water color at %s: %s", locationCode, metrics.get("colour"));
        } else {
            response = "🤖 I'm unsure what you meant. Try asking about pH, chlorine, or colour.";
        }

        return ResponseEntity.ok(response);
    }

    private String extractLocationCode(String query) {
        String[] keywords = {"village", "city", "location"};
        String[] words = query.toLowerCase().split("\\s+");

        for (int i = 0; i < words.length; i++) {
            for (String keyword : keywords) {
                if (words[i].equals(keyword) && i + 1 < words.length) {
                    return words[i + 1];
                }
            }
        }

        return "";
    }



}