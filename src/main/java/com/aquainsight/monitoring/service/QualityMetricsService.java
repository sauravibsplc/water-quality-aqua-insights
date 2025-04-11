package com.aquainsight.monitoring.service;


import com.aquainsight.monitoring.model.WaterSample;
import com.aquainsight.monitoring.repository.WaterSampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QualityMetricsService {

    @Autowired
    private WaterSampleRepository repository;

    public WaterSample recordSample(WaterSample sample) {
        return repository.save(sample);
    }

    public List<WaterSample> fetchSamplesByLocation(String locationCode) {
        System.out.println("Location Code: " + locationCode);
        return repository.findByLocationCode(locationCode);
    }

    public String handleUserQuery(String query) {
        // Placeholder for advanced query logic
        return "Received query: " + query;
    }
}
