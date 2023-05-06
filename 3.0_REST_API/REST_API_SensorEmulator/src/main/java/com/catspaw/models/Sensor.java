package com.catspaw.models;


public class Sensor {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Sensor{" +
                "name='" + name + '\'' +
                '}';
    }
}
