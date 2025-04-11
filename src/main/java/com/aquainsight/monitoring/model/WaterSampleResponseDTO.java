package com.aquainsight.monitoring.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class WaterSampleResponseDTO {
    private Long id;
    private String locationCode;
    private LocalDate dateCollected;
    private Map<String, Double> qualityMetrics;
}

