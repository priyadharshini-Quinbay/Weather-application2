package com.example.Forecast.dto;

import java.util.Date;

public class CurrentWeekStatus {
//    String id;
    String city;
    String date;
    Float avgHumidity;
    Float avgPrecipitation;
    Float avgTemperature;
    Float maxTemperature;
    Float minTemperature;
    String weatherType;
    String weathergif;
    String weatherimage;

    public CurrentWeekStatus(String city, String date, Float avgHumidity, Float avgPrecipitation, Float avgTemperature, Float maxTemperature, Float minTemperature, String weatherType, String weathergif, String weatherimage) {
        this.city = city;
        this.date = date;
        this.avgHumidity = avgHumidity;
        this.avgPrecipitation = avgPrecipitation;
        this.avgTemperature = avgTemperature;
        this.maxTemperature = maxTemperature;
        this.minTemperature = minTemperature;
        this.weatherType = weatherType;
        this.weathergif = weathergif;
        this.weatherimage = weatherimage;
    }
    public String getWeathergif() {

        return weathergif;
    }

    public void setWeathergif(String weathergif) {
        this.weathergif = weathergif;
    }

    public String getWeatherimage() {
        return weatherimage;
    }

    public void setWeatherimage(String weatherimage) {
        this.weatherimage = weatherimage;
    }



    public CurrentWeekStatus() {

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

    public Float getAvgHumidity() {
        return avgHumidity;
    }

    public void setAvgHumidity(Float avgHumidity) {
        this.avgHumidity = avgHumidity;
    }

    public Float getAvgPrecipitation() {
        return avgPrecipitation;
    }

    public void setAvgPrecipitation(Float avgPrecipitation) {
        this.avgPrecipitation = avgPrecipitation;
    }

    public Float getAvgTemperature() {
        return avgTemperature;
    }

    public void setAvgTemperature(Float avgTemperature) {
        this.avgTemperature = avgTemperature;
    }

    public Float getMaxTemperature() {
        return maxTemperature;
    }

    public void setMaxTemperature(Float maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public Float getMinTemperature() {
        return minTemperature;
    }

    public void setMinTemperature(Float minTemperature) {
        this.minTemperature = minTemperature;
    }

    public String getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(String weatherType) {
        this.weatherType = weatherType;
    }

}
