package com.aquainsight.monitoring.controller;



import com.aquainsight.monitoring.model.WaterSample;
import com.aquainsight.monitoring.service.QualityMetricsService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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
    public void testIngestData() throws Exception {
        WaterSample waterQuality = new WaterSample();
        waterQuality.setLocationCode("village1");
        waterQuality.setDateCollected(LocalDate.now());
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("pH", 7.0);
        parameters.put("chlorine", 0.5);
        waterQuality.setQualityMetrics(parameters); // Set parameters as a Map

        Mockito.when(waterQualityService.recordSample(Mockito.any())).thenReturn(waterQuality);

        // Construct the request body with parameters as an object
        String requestBody = "{\n" +
                "  \"locationCode\": \"village1\",\n" +
                "  \"dateCollected\": \"2024-01-01\",\n" +
                "  \"qualityMetrics\": {\n" +
                "    \"pH\": 7.0,\n" +
                "    \"chlorine\": 0.8,\n" +
                "    \"colour\": \"clear\"\n" +
                "  }\n" +
                "}\n";

        mockMvc.perform(post("/api/water-quality")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

}
