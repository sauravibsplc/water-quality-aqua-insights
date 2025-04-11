package com.aquainsight.monitoring.controller;

import com.aquainsight.monitoring.exception.DataUnavailableException;
import com.aquainsight.monitoring.model.WaterQualityRequestDTO;
import com.aquainsight.monitoring.model.WaterSample;
import com.aquainsight.monitoring.model.WaterSampleResponseDTO;
import com.aquainsight.monitoring.service.QualityMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/metrics")
public class MetricsController {

    @Autowired
    private QualityMetricsService metricsService;

    @PostMapping
    public ResponseEntity<WaterSampleResponseDTO> submitMetrics(@RequestBody WaterQualityRequestDTO qualityMetrics) {
        WaterSampleResponseDTO stored = metricsService.recordSample(qualityMetrics);
        return ResponseEntity.ok(stored);
    }

    @GetMapping("/{locationId}")
    public ResponseEntity<List<WaterSampleResponseDTO>> fetchMetrics(@PathVariable String locationId) {
        List<WaterSampleResponseDTO> results = metricsService.fetchSamplesByLocation(locationId);

        if (results.isEmpty()) {
            throw new DataUnavailableException("No metrics found for location ID: " + locationId);
        }

        return ResponseEntity.ok(results);
    }


}
