package com.aquainsight.monitoring.model;


import com.aquainsight.monitoring.service.MapToJsonConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Map;
@Data
@Entity
@Table(name = "water_quality_records")
public class WaterSample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String locationCode;

    private LocalDate dateCollected;

    @Convert(converter = MapToJsonConverter.class)
    private Map<String, Object> qualityMetrics;

}
