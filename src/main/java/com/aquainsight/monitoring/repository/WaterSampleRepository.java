package com.aquainsight.monitoring.repository;



import com.aquainsight.monitoring.model.WaterSample;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WaterSampleRepository extends JpaRepository<WaterSample, Long> {
    List<WaterSample> findByLocationCode(String villageId);
}

