package com.catspaw.rest_api_app.repositories;

import com.catspaw.rest_api_app.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasurementRepository extends JpaRepository<Measurement,Integer> {
    @Query(value = "select count(*) from Measurement where raining = true")
    long countRainyDays();
}
