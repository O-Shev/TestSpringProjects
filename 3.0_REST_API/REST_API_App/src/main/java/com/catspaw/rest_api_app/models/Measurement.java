package com.catspaw.rest_api_app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Cascade;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "Measurements")
public class Measurement {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;

    @Column(name = "temperature")
    @NotNull(message = "Temperature not should be null")
    private float temperature;

    @Column(name = "raining")
    @NotNull(message = "Raining should not be null")
    private boolean raining;

    @Column(name = "measure_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date measure_at;

    public Measurement() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
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
