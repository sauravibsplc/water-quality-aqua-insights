package com.aquainsight.monitoring.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "water_quality_records")
public class WaterSample {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String locationCode;

    private LocalDate dateCollected;

    @OneToMany(mappedBy = "sample", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<WaterQualityMetric> qualityMetrics = new ArrayList<>();

}
