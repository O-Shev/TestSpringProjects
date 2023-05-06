package com.catspaw.rest_api_app.util;

import com.catspaw.rest_api_app.models.Sensor;
import com.catspaw.rest_api_app.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class SensorValidator implements Validator {

    private final SensorRepository sensorRepository;

    @Autowired
    public SensorValidator(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Sensor.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;
        Optional<Sensor> findByNameSensor = sensorRepository.findByName(sensor.getName()).stream().findAny();
        if(findByNameSensor.isPresent()){
            errors.rejectValue("name", "", "Sensor with this name already exist");
        }
    }
}
