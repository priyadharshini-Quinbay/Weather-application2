package com.example.Forecast.dto;

import org.springframework.format.annotation.DateTimeFormat;


public class CurrentHourStatus {
    private String id;

    private String city;

    public CurrentHourStatus(String city, String date, int startTime, Float humidity, Float precipitation, Float temperature, String weatherType, String weathergif, String weathericon) {
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

    public CurrentHourStatus(String id, String city, String date, int startTime, Float humidity, Float precipitation, Float temperature, String weatherType, String weathergif, String weathericon) {

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

    private String date;

    private int startTime;

    private Float humidity;
    private  Float precipitation;
    private  Float temperature;
    private String weatherType;
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


    public CurrentHourStatus() {
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
