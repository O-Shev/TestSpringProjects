package com.catspaw.rest_api_app.controllers;


import com.catspaw.rest_api_app.dto.MeasurementDto;
import com.catspaw.rest_api_app.models.Measurement;
import com.catspaw.rest_api_app.services.MeasurementService;
import com.catspaw.rest_api_app.util.MeasurementErrorResponse;
import com.catspaw.rest_api_app.util.MeasurementNotAddException;
import com.catspaw.rest_api_app.util.MeasurementValidator;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/measurements")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementController(MeasurementService measurementService, ModelMapper modelMapper, MeasurementValidator measurementValidator) {
        this.measurementService = measurementService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }


    @GetMapping
    @ResponseBody
    public List<MeasurementDto> getMeasurements(){
        List<Measurement> measurements = measurementService.index();
        List<MeasurementDto> measurementsDto = new ArrayList<>(measurements.size());
        for(int i = 0; i < measurements.size(); i++){
            measurementsDto.add(modelMapper.map(measurements.get(i), MeasurementDto.class));
            measurementsDto.get(i).setValue(measurements.get(i).getTemperature());
        }
        return measurementsDto;
    }

    @GetMapping("/rainyDaysCount")
    @ResponseBody
    public long rainyDaysCount(){
        return measurementService.rainDaysCount();
    }


    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDto measurementDto,
                                                     BindingResult bindingResult){

        Measurement measurement = modelMapper.map(measurementDto, Measurement.class);
        measurement.setTemperature(measurementDto.getValue());
        measurementValidator.validate(measurement, bindingResult);
        if(bindingResult.hasErrors()){
            StringBuilder msg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors){
                msg
                        .append(error.getField())
                        .append(" - ").append(error.getDefaultMessage()).append("; ");
            }
            throw new MeasurementNotAddException(msg.toString());
        }
        measurementService.addMeasurement(measurement);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<MeasurementErrorResponse> handleException(MeasurementNotAddException e){
        MeasurementErrorResponse response = new MeasurementErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
