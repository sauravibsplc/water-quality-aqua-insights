package com.aquainsight.monitoring.controller;



import com.aquainsight.monitoring.model.WaterQualityMetric;
import com.aquainsight.monitoring.model.WaterSample;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.aquainsight.monitoring.model.WaterSampleResponseDTO;
import com.aquainsight.monitoring.service.QualityMetricsService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WaterQualityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QualityMetricsService waterQualityService;

    @Test
    public void testGetData_NoDataFound() throws Exception {
        Mockito.when(waterQualityService.fetchSamplesByLocation("village1")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/water-quality/village1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testSubmitMetrics() throws Exception {
        WaterSampleResponseDTO responseDTO = new WaterSampleResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setLocationCode("village1");
        responseDTO.setDateCollected(LocalDate.parse("2024-01-01"));

        Map<String, Double> metricsMap = new HashMap<>();
        metricsMap.put("pH", 7.0);
        metricsMap.put("chlorine", 0.8);
        responseDTO.setQualityMetrics(metricsMap);

        Mockito.when(waterQualityService.recordSample(Mockito.any())).thenReturn(responseDTO);

        String requestBody = "{\n" +
                "  \"locationCode\": \"village1\",\n" +
                "  \"dateCollected\": \"2024-01-01\",\n" +
                "  \"qualityMetrics\": {\n" +
                "    \"pH\": 7.0,\n" +
                "    \"chlorine\": 0.8\n" +
                "  }\n" +
                "}";

        mockMvc.perform(post("/api/metrics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().string(Matchers.containsString("village1")))
                .andExpect(content().string(Matchers.containsString("pH")))
                .andExpect(content().string(Matchers.containsString("7.0")));
    }
}
