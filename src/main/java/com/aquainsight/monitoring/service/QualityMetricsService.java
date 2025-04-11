package com.aquainsight.monitoring.service;


import com.aquainsight.monitoring.model.WaterQualityMetric;
import com.aquainsight.monitoring.model.WaterQualityRequestDTO;
import com.aquainsight.monitoring.model.WaterSample;
import com.aquainsight.monitoring.model.WaterSampleResponseDTO;
import com.aquainsight.monitoring.repository.WaterSampleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QualityMetricsService {

    @Autowired
    private WaterSampleRepository repository;

    public WaterSampleResponseDTO recordSample(WaterQualityRequestDTO dto) {

        WaterSample sample = new WaterSample();
        sample.setLocationCode(dto.getLocationCode());
        sample.setDateCollected(dto.getDateCollected());

        List<WaterQualityMetric> metrics = dto.getQualityMetrics().entrySet().stream()
                .map(entry -> {
                    WaterQualityMetric metric = new WaterQualityMetric();
                    metric.setParameter(entry.getKey());
                    metric.setMetricValue(entry.getValue());
                    metric.setSample(sample); // Set FK relationship
                    return metric;
                })
                .collect(Collectors.toList());

        sample.setQualityMetrics(metrics);

        WaterSample sampleres =  repository.save(sample);
        WaterSampleResponseDTO response = mapToDTO(sampleres);
        return response;
    }

    public WaterSampleResponseDTO mapToDTO(WaterSample sample) {
        WaterSampleResponseDTO dto = new WaterSampleResponseDTO();
        dto.setId(sample.getId());
        dto.setLocationCode(sample.getLocationCode());
        dto.setDateCollected(sample.getDateCollected());

        Map<String, Double> metricsMap = sample.getQualityMetrics().stream()
                .collect(Collectors.toMap(
                        WaterQualityMetric::getParameter,
                        WaterQualityMetric::getMetricValue
                ));
        dto.setQualityMetrics(metricsMap);

        return dto;
    }


    public List<WaterSampleResponseDTO> fetchSamplesByLocation(String locationCode) {
        System.out.println("Location Code: " + locationCode);
        List<WaterSample> samples = repository.findByLocationCode(locationCode);

        return samples.stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public String handleUserQuery(String query) {
        // Placeholder for advanced query logic
        return "Received query: " + query;
    }

    private WaterSampleResponseDTO mapToResponseDTO(WaterSample sample) {
        WaterSampleResponseDTO dto = new WaterSampleResponseDTO();
        dto.setId(sample.getId());
        dto.setLocationCode(sample.getLocationCode());
        dto.setDateCollected(sample.getDateCollected());

        Map<String, Double> metricsMap = sample.getQualityMetrics().stream()
                .collect(Collectors.toMap(
                        WaterQualityMetric::getParameter,
                        WaterQualityMetric::getMetricValue
                ));

        dto.setQualityMetrics(metricsMap);
        return dto;
    }
}
