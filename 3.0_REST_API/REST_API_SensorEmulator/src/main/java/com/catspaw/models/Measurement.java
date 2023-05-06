package com.catspaw.models;


import java.util.Date;

public class Measurement {


    private Sensor sensor;

    private float value;

    private boolean raining;

    private Date measure_at;

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
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

    @Override
    public String toString() {
        return "Measurement{" +
                "sensor=" + sensor +
                ", value=" + value +
                ", raining=" + raining +
                ", measure_at=" + measure_at +
                '}';
    }
}
