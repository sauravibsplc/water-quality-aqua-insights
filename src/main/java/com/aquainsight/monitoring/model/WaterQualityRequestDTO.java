package com.aquainsight.monitoring.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Map;

@Data
@Getter
@Setter
public class WaterQualityRequestDTO {
    private String locationCode;
    private LocalDate dateCollected;
    private Map<String, Double> qualityMetrics;
}
