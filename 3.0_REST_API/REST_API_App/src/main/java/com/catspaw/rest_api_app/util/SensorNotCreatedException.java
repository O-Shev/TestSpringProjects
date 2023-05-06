package com.catspaw.rest_api_app.util;

public class SensorNotCreatedException extends RuntimeException{
    public SensorNotCreatedException(String msg){
        super(msg);
    }
}
