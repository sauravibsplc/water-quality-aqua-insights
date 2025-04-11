package com.aquainsight.monitoring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "water_quality_metric")
public class WaterQualityMetric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String parameter;
    private Double metricValue;

    @ManyToOne
    @JoinColumn(name = "sample_id")

    private WaterSample sample;
}

