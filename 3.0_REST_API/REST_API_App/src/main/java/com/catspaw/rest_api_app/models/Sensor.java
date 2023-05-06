package com.catspaw.rest_api_app.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "sensors")
public class Sensor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "Name should not be empty")
    @Pattern(regexp = "[a-zA-Z]{3,30}", message = "Name should have length between 3 and 30")
    private String name;

    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL)
    private List<Measurement> measurements;

    public Sensor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(List<Measurement> measurements) {
        this.measurements = measurements;
    }
}
