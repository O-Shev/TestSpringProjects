package com.catspaw.rest_api_app.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class SensorDto {

    @NotEmpty(message = "Name should not be empty")
    @Pattern(regexp = "[a-zA-Z]{3,30}", message = "Name should have length between 3 and 30")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
