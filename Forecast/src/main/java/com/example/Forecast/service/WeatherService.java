package com.example.Forecast.service;


import com.example.Forecast.dto.CurrentWeekStatus;
import com.example.Forecast.dto.ReturnAfterSavingData;
import com.example.Forecast.entity.CurrentHourStatusEntity;

import java.text.ParseException;
import java.util.List;

public interface WeatherService {
    ReturnAfterSavingData getCurrentWeather(String city, String date, int time) ;
    List<CurrentWeekStatus> getCurrentNextSix(String city, String date) throws ParseException;
    List<CurrentHourStatusEntity> getCurrentRange(String city, String date,int starttime) throws ParseException;
    ReturnAfterSavingData addWeatherHour(CurrentHourStatusEntity weather);
    ReturnAfterSavingData updateWeatherHour(CurrentHourStatusEntity weather);

    List<String> getCity();
//    List<WeatherImageEntity> getImage1(String weather, int i);
//    void addImage(WeatherImageEntity data);
//    List<Ads> getAds() ;
//    List<Brands> getBrands() ;
//
//
//    Ads addAds(Ads ads);
//    Brands addBrands(Brands ads);
//
//    List<LikeAds> getAllLikes() ;
//
//    LikeAds addLikes(LikeAds like);
//
//    List<Analysis> Analysis() ;
//    List<Analysis> Analysis2() ;
//    List<Analysis> Analysis3() ;
//    List<Analysis> Analysis4() ;
//
//
//    List<UserEntity> getUser() ;
//    ReturnAfterSavingData addUser(UserEntity like);
//    void addWeatherWeek(CurrentWeekStatusEntity weather);
//    CurrentHourStatusEntity updateWeather(CurrentHourStatusEntity weather);

}
