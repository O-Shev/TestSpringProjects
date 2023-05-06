package com.catspaw.rest_api_app.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Date;

public class MeasurementDto {

    @NotNull(message = "Sensor not should be null")
    private SensorDto sensor;

    @NotNull(message = "Temperature not should be null")
    private float value;

    @NotNull(message = "Raining should not be null")
    private boolean raining;

    private Date measure_at;

    public SensorDto getSensor() {
        return sensor;
    }

    public void setSensor(SensorDto sensor) {
        this.sensor = sensor;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Date getMeasure_at() {
        return measure_at;
    }

    public void setMeasure_at(Date measure_at) {
        this.measure_at = measure_at;
    }
}
