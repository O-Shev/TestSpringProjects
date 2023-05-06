package com.catspaw.rest_api_app.services;

import com.catspaw.rest_api_app.models.Measurement;
import com.catspaw.rest_api_app.repositories.MeasurementRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SessionFactory sessionFactory;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SessionFactory sessionFactory) {
        this.measurementRepository = measurementRepository;
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public void addMeasurement(Measurement measurement){
        measurement.setMeasure_at(new Date());
        measurementRepository.save(measurement);
    }

    public List<Measurement> index(){
        return measurementRepository.findAll();
    }

    public long rainDaysCount(){
        return measurementRepository.countRainyDays();
    }
}
