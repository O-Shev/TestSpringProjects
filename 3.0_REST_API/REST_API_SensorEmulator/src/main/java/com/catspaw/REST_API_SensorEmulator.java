package com.catspaw;

import com.catspaw.models.Measurement;
import com.catspaw.models.Sensor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class REST_API_SensorEmulator {
    public static void main(String[] args) {
        String urlRegSensor = "http://localhost:8080/sensors/registration";
        String urlAddMeasure = "http://localhost:8080/measurements/add";
        String urlGetMeasure = "http://localhost:8080/measurements";
        String nameNewSensor = "SensorThree";

        RestTemplate restTemplate = new RestTemplate();

        //Registration new sensor //SensorName each turn should be another
        Map<String, String> registartionMap = new HashMap<>();
        registartionMap.put("name",nameNewSensor);
        HttpEntity<Map<String, String>> requestRegistration =new HttpEntity<>(registartionMap);
        String responseRegistration = restTemplate.postForObject(urlRegSensor, requestRegistration, String.class);
        System.out.println(responseRegistration);

        //Add 1000 measurements
        Measurement measurement = new Measurement();
        Sensor sensor = new Sensor();
        sensor.setName(nameNewSensor);
        measurement.setSensor(sensor);
        Random random = new Random();
        float probability = 0.68f;
        for(int i = 0; i < 1000; i++){
            measurement.setRaining(random.nextFloat() < probability);
            measurement.setValue((float)(random.nextGaussian()*3+24));
            HttpEntity<Measurement> requestAddMeasure = new HttpEntity<>(measurement);
            String responseAddMeasure = restTemplate.postForObject(urlAddMeasure, requestAddMeasure, String.class);
        }

        //Get All Measurements
        String strResponseGetMeasurements = restTemplate.getForObject(urlGetMeasure, String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<Measurement> objListMeasurements;
        try {
            objListMeasurements = mapper.readValue(strResponseGetMeasurements, new TypeReference<List<Measurement>>() {});
            for(Measurement m : objListMeasurements){System.out.println(m);}
        } catch (Exception e){System.out.println(e);}


    }
}