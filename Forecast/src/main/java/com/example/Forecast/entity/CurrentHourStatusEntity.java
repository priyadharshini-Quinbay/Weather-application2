package com.example.Forecast.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;

@Document
public class CurrentHourStatusEntity implements Serializable {
    @Id
    String id;

    private String city;

    private String date;
    private int startTime;
    private Float humidity;
    private  Float precipitation;
    private String weatherType;
    private Float temperature;
   private String weathergif;
   private String weathericon;

    public String getWeathergif() {
        return weathergif;
    }

    public void setWeathergif(String weathergif) {
        this.weathergif = weathergif;
    }

    public String getWeathericon() {
        return weathericon;
    }

    public void setWeathericon(String weathericon) {
        this.weathericon = weathericon;
    }



    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public CurrentHourStatusEntity() {
    }
    public CurrentHourStatusEntity(String id,String city, String date, int startTime, Float humidity, Float precipitation, Float temperature, String weatherType, String weathergif, String weathericon) {
        this.id = id;
        this.city = city;
        this.date = date;
        this.startTime = startTime;
        this.humidity = humidity;
        this.precipitation = precipitation;
        this.temperature = temperature;
        this.weatherType = weatherType;
        this.weathergif = weathergif;
        this.weathericon = weathericon;
    }

    public CurrentHourStatusEntity(String city,String date, int startTime, Float humidity, Float precipitation, String weatherType, Float temperature) {

        this.city = city;
        this.date = date;
        this.startTime = startTime;
        this.humidity = humidity;
        this.precipitation = precipitation;
        this.temperature = temperature;
        this.weatherType = weatherType;
    }

    public CurrentHourStatusEntity(String id, String city, Float altitude, String date, int startTime, Float humidity, Float precipitation, String weatherType, Float temperature) {

        this.id = id;
        this.city = city;
        this.date = date;
        this.startTime = startTime;
        this.humidity = humidity;
        this.precipitation = precipitation;
        this.temperature = temperature;
        this.weatherType = weatherType;
    }
    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }


    public Float getHumidity() {
        return humidity;
    }

    public void setHumidity(Float humidity) {
        this.humidity = humidity;
    }

    public Float getPrecipitation() {
        return precipitation;
    }

    public void setPrecipitation(Float precipitation) {
        this.precipitation = precipitation;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }
}