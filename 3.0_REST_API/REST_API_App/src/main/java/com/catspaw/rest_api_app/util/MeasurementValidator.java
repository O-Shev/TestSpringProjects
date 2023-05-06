package com.catspaw.rest_api_app.util;

import com.catspaw.rest_api_app.models.Measurement;
import com.catspaw.rest_api_app.models.Sensor;
import com.catspaw.rest_api_app.repositories.SensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
public class MeasurementValidator implements Validator {

    private final SensorRepository sensorRepository;
    @Autowired
    public MeasurementValidator(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Measurement.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Measurement measurement = (Measurement) target;
        Optional<Sensor> findByName = sensorRepository.findByName(measurement.getSensor().getName()).stream().findAny();
        if(findByName.isPresent())
            measurement.setSensor(findByName.get());
        else errors.rejectValue("Sensor","","Sensor with this name not exist");
        if(!(-100 <= measurement.getTemperature() && measurement.getTemperature() <= 100))
            errors.rejectValue("Value","","Value is not in range [-100, 100]");
    }
}
