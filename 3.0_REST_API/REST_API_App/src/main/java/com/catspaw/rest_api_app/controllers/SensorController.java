package com.catspaw.rest_api_app.controllers;

import com.catspaw.rest_api_app.dto.SensorDto;
import com.catspaw.rest_api_app.models.Sensor;
import com.catspaw.rest_api_app.services.SensorService;
import com.catspaw.rest_api_app.util.SensorErrorResponse;
import com.catspaw.rest_api_app.util.SensorNotCreatedException;
import com.catspaw.rest_api_app.util.SensorValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sensors")
public class SensorController {
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public SensorController(SensorService sensorService, SensorValidator sensorValidator, ModelMapper modelMapper) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid SensorDto sensorDto,
                                                   BindingResult bindingResult){
        Sensor sensor = modelMapper.map(sensorDto, Sensor.class);
        sensorValidator.validate(sensor, bindingResult);
        if(bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();

            for(FieldError error : errors){
                errorMsg.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append("; ");
            }
            throw new SensorNotCreatedException(errorMsg.toString());
        }
        sensorService.addNewSensor(sensor);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e){
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
