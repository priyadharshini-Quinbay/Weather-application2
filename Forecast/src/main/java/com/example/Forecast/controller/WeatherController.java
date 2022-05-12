package com.example.Forecast.controller;
import com.example.Forecast.ForecastApplication;
import com.example.Forecast.dto.Response;
import com.example.Forecast.dto.CurrentWeekStatus;
import com.example.Forecast.dto.ReturnAfterSavingData;
import com.example.Forecast.entity.CurrentHourStatusEntity;
import com.example.Forecast.service.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/weather-application")
public class WeatherController {
    @Autowired
    WeatherService weather;
    SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
    Date date = new Date();
    SimpleDateFormat timeformatter = new SimpleDateFormat("HH");
    Logger logger = LoggerFactory.getLogger(ForecastApplication.class);
//Api to fetch current weather report.
    @GetMapping(value="/weather-report/present/{city}/{date}/{time}")
    public Response<CurrentHourStatusEntity> getpresentweathereport(@PathVariable(value="city") String city, @PathVariable(value="date") String date, @PathVariable(value="time") int time) {

        ReturnAfterSavingData result =weather.getCurrentWeather(city,date,time);

        if (result.getVal() == 1) {
            logger.error("Retrieved current weather report Successfully");
            return new Response<>(result.getCurrentHourStatus());
        } else if (result.getVal() == -1) {
            logger.error("Error getting current weather report");

            return new Response<>(1001, "Error getting current weather report");
        }
        else  {
            logger.error("Error getting current weather report");

            return new Response<>(1002, "Error getting current  report");
        }
        }
//Api to fetch city.
    @GetMapping(value="/city")
    public Response<List<String>> getcity() {

        List<String> result= weather.getCity();
        if(result!=null) {
            logger.error("Retrieved cities Successfully");

            return new Response<>(result);
        }
        else {
            logger.error("Error getting cities ");
            return new Response<>(1001, "Error while Getting City");
        }
        }
//Api to fetch next six hours weather report
    @GetMapping(value="/weather-report/next-six-hours/{city}/{date}/{time}")
    public Response<List<CurrentHourStatusEntity>> gethourlyweathereport(@PathVariable(value="city") String city,@PathVariable(value="date") String date,@PathVariable(value="time") int time) throws ParseException {
        List<CurrentHourStatusEntity> result = weather.getCurrentRange(city, date, time);
        if (result != null) {
            logger.error("Retrieved next 6 days weather report Successfully");
            return new Response<>(result);
        } else {
            return new Response<>(1001, "Error while Getting next six hours weather report");
        }
    }
//Api to fetch next six days weather report
    @GetMapping(value="/weather-report/next-six-days/{city}/{date}")
    public Response<List<CurrentWeekStatus>> getnextsixdaysweathereport(@PathVariable(value="city") String city, @PathVariable(value="date") String date) throws ParseException {

        List<CurrentWeekStatus> result = weather.getCurrentNextSix(city,date);
        if(result!=null) {
            logger.error("Retrieved next six days Successfully");
            return new Response<>(result);
        }
        else {
            logger.error("Error getting next six days weather report ");
            return new Response<>(1001, "Error while Getting next six days weather report ");
        }
    }
//Api to add hourly data.
    @PostMapping("/weather-report/add-hourly-data/")
    public Response<CurrentHourStatusEntity> addhourlydata(@RequestBody CurrentHourStatusEntity data) {

        ReturnAfterSavingData result = weather.addWeatherHour(data);
        if (result.getVal() == 1) {
            logger.error("Added hourly data Successfully");
            return new Response<CurrentHourStatusEntity>(result.getCurrentHourStatus());
        }
        else {
            logger.error(geterrorcode(result.getVal()).getErrorMessage());
            return geterrorcode(result.getVal());
        }
        }
//Api to update hourly data.
    @PutMapping("/weather-report/update-hourly-data/")
    public Response<CurrentHourStatusEntity> updatehourlydata(@RequestBody CurrentHourStatusEntity data) {

        ReturnAfterSavingData result = weather.updateWeatherHour(data);
        if (result.getVal() == 1) {
            logger.error("Updated hourly data Successfully");
            return new Response<CurrentHourStatusEntity>(result.getCurrentHourStatus());
        }
        else {
            logger.error(geterrorcode(result.getVal()).getErrorMessage());
            return geterrorcode(result.getVal());
        }

    }
//Api to fetch current weather report by passing only city.
    @GetMapping(value="/weather-report/present/{city}")
    public Response<CurrentHourStatusEntity> getpresentweathereportwithcity(@PathVariable(value="city") String city) {

        ReturnAfterSavingData result =weather.getCurrentWeather(city,dateformatter.format(date), Integer.parseInt(timeformatter.format(date)));

        if (result.getVal() == 1) {
            logger.error("Retrieved current weather report Successfully");
            return new Response<>(result.getCurrentHourStatus());
        } else if (result.getVal() == -1) {
            logger.error("Error getting current weather report ");
            return new Response<>(1001, "Error getting current weather report");
        }
        else  {
            logger.error("Error getting current weather report ");
            return new Response<>(1002, "Error getting current weather report");
        }
        }
//Api to fetch next six hours weather report by passing only city.
    @GetMapping(value="/weather-report/next-six-hours/{city}")
    public  Response<List<CurrentHourStatusEntity>> gethourlyweathereportwithcity(@PathVariable(value="city") String city) throws ParseException  {

        List<CurrentHourStatusEntity> result = weather.getCurrentRange(city,dateformatter.format(date), Integer.parseInt(timeformatter.format(date)));
        if(result!=null) {
            logger.error("Retrieved next 6 hours weather report Successfully");
            return new Response<>(result);
        }
        else {
            logger.error("Error getting current weather report ");
            return new Response<>(1001, "Error while Getting Next Six Hours Weather Report");
        }
    }
//Api to fetch next six days weather report by passing only city.
    @GetMapping(value="/weather-report/next-six-days/{city}")
    public  Response<List<CurrentWeekStatus>> getnextsixdaysweathereportwithcity(@PathVariable(value="city") String city) throws ParseException {
        SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();


            List<CurrentWeekStatus> result = weather.getCurrentNextSix(city,dateformatter.format(date));

        if(result!=null) {
            logger.error("Retrieved next 6 days weather report Successfully");
            return new Response<>(result);
        } else {
            logger.error("Error getting current weather report ");
            return new Response<>(1001, "Error while Getting Next Six Days Weather Report");
        }
    }
//function to get error code.
    public Response<CurrentHourStatusEntity> geterrorcode(int val)
    {
        switch (val){

    case-1:
        return new Response<>(1001, "Error entering city");

    case-2:
        return new Response<>(1002, "Enter start time between 0 to 23");
    case-3:
        return new Response<>(1003, "Error entering date");
    case-4:
        return new Response<>(1004, "Error entering humidity");
    case-5:
        return new Response<>(1005, "Enter valid  humidity in %");
    case-6:
        return new Response<>(1006, "Error entering percipitation");
    case-7:
        return new Response<>(1007, "Enter valid percipitation in %");
    case-8:
        return new Response<>(1008, "Error entering temperature");
    case-9:
        return new Response<>(1009, "Enter valid city");
    case-10:
        return new Response<>(1010, "Enter only alphabets in city");
    case-11:
        return new Response<>(1011, "Enter start time between 0 to 23");
    case-12:
        return new Response<>(1012, "Data already present");
    case-13:
        return new Response<>(1013, "Data not present");
    default:
        return new Response<>(1014, "Error entering data");

}

    }

}
