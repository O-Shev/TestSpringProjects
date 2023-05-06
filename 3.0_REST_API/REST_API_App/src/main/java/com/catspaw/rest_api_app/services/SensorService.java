package com.catspaw.rest_api_app.services;

import com.catspaw.rest_api_app.models.Sensor;
import com.catspaw.rest_api_app.repositories.SensorRepository;
import com.catspaw.rest_api_app.util.SensorValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    @Autowired
    public SensorService(SensorRepository sensorRepository, SensorValidator sensorValidator) {
        this.sensorRepository = sensorRepository;
    }

    @Transactional
    public void addNewSensor(Sensor sensor){
        sensorRepository.save(sensor);
    }
}
