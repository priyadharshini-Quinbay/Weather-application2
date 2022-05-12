package com.example.Forecast.service;
import com.example.Forecast.dto.CurrentWeekStatus;
import com.example.Forecast.dto.ReturnAfterSavingData;
import com.example.Forecast.entity.CurrentHourStatusEntity;
import com.example.Forecast.repository.CurrentHourStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import static org.apache.commons.validator.GenericValidator.isDate;

@Service
public class WeatherServiceImplementation implements WeatherService {
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


    @Autowired
    CurrentHourStatusRepo currentHourStatusRepo;
    // function to add hourly data .
    public ReturnAfterSavingData addWeatherHour(CurrentHourStatusEntity data) {
        String[] image;
        String weathertype = "";
        CurrentHourStatusEntity ad = new CurrentHourStatusEntity();
        if (data.getCity() != "" && data.getCity() != null && data.getCity().matches("[a-zA-Z]+") && (data.getStartTime() >= 0 && data.getStartTime() < 24) && isDate(data.getDate(), "yyyy-MM-dd", true) == true && data.getHumidity() != null && data.getHumidity() >= 0 && data.getHumidity() <= 100 && data.getTemperature() != null && data.getPrecipitation() != null && data.getPrecipitation() >= 0 && data.getPrecipitation() <= 100) {
            ad = currentHourStatusRepo.findFirstByCityAndDateAndStartTime(data.getCity(), data.getDate(), data.getStartTime());
            if (ad == null) {
                weathertype = setWeather(data.getPrecipitation(), data.getStartTime(), data.getTemperature());
                data.setWeatherType(weathertype);
                image = getimages(weathertype);
                data.setWeathergif(image[0]);
                data.setWeathericon(image[1]);
                currentHourStatusRepo.save(data);
                ad = data;
            } else
                return new ReturnAfterSavingData(-12, ad);
        } else
            ad = null;
        ReturnAfterSavingData returnresult=geterrorcode(data);
        if(returnresult.getVal()==1)
            return new ReturnAfterSavingData(1, ad);
        else
            return returnresult;
    }
//function to update the hourly data and cache update also.
    @CachePut(cacheNames = "current-weather", key = "#data.city+#data.date+#data.startTime")
    public ReturnAfterSavingData updateWeatherHour(CurrentHourStatusEntity data) {
        String[] image;
        String weathertype = "";
        CurrentHourStatusEntity ad = new CurrentHourStatusEntity();
        if (data.getCity() != "" && data.getCity() != null && data.getCity().matches("[a-zA-Z]+") && (data.getStartTime() >= 0 && data.getStartTime() < 24) && isDate(data.getDate(), "yyyy-MM-dd", true) == true && data.getHumidity() != null && data.getHumidity() >= 0 && data.getHumidity() <= 100 && data.getTemperature() != null && data.getPrecipitation() != null && data.getPrecipitation() >= 0 && data.getPrecipitation() <= 100) {
            ad = currentHourStatusRepo.findFirstByCityAndDateAndStartTime(data.getCity(), data.getDate(), data.getStartTime());
            if (ad != null) {
                ad.setHumidity(data.getHumidity());
                ad.setPrecipitation(data.getPrecipitation());
                ad.setTemperature(
                        data.getTemperature());
                weathertype = setWeather(data.getPrecipitation(), data.getStartTime(), data.getTemperature());
                ad.setWeatherType(weathertype);
                image = getimages(weathertype);
                ad.setWeathergif(image[0]);
                ad.setWeathericon(image[1]);
                currentHourStatusRepo.save(ad);
            } else
                return new ReturnAfterSavingData(-13, null);
        } else
            ad = null;
        ReturnAfterSavingData returnresult=geterrorcode(data);
        if(returnresult.getVal()==1)
            return new ReturnAfterSavingData(1, ad);
        else
            return returnresult;
        }
    //function to get the current weather report  and cache adds data also.
    @Cacheable(cacheNames = "current-weather",key = "#city + #date + #time")
    public ReturnAfterSavingData getCurrentWeather(String city, String date, int time) {
        CurrentHourStatusEntity list1 =currentHourStatusRepo.findFirstByCityAndDateAndStartTime(city,date,time);

        if(list1!=null)
                    return new ReturnAfterSavingData(1 , list1);

        else {
            return new ReturnAfterSavingData(-1 , null);
        }
    }

    //function to get the next six days weather report.
    public List<CurrentWeekStatus> getCurrentNextSix(String city, String date) throws ParseException
    {
        Date date1 = format.parse(date);
        calendar.setTime(date1);
        List<CurrentWeekStatus> nextsixdayslist = new ArrayList<>();
        for(int i=0;i<6;i++){
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            date = format.format(calendar.getTime());
            List<CurrentHourStatusEntity> weekWeatherEntityList=currentHourStatusRepo.findByCityAndDate(city,date) ;
            if(weekWeatherEntityList.size()!=0)
            {
                nextsixdayslist.add(getAveragefornextsixdays(weekWeatherEntityList));
            }
            else{
                nextsixdayslist.add(new CurrentWeekStatus(city,date,0f,0f,0f,
                        0f,0f,"not available",
                        "/assets/gif/na.gif",
                        "/assets/na.webp"));
            }

        }
            return nextsixdayslist;
        }
    //function to get the next six hours weather report.
    public List<CurrentHourStatusEntity> getCurrentRange(String city, String date,int starttime) throws ParseException{
        List<CurrentHourStatusEntity> list1 =new ArrayList<>();
        Date dates = format.parse(date);
        calendar.setTime(dates);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        String nextdate = format.format(calendar.getTime());
        int t=1;
        while(starttime<=24&&t<=6) {
            switch (starttime) {
                case 23:
                    starttime = 0;
                    date = nextdate;
                    t += 1;

                    break;
                default:
                    starttime += 1;
                    t += 1;
                    break; }
            if (currentHourStatusRepo.findFirstByCityAndDateAndStartTime(city, date, starttime) != null)
                list1.add(currentHourStatusRepo.findFirstByCityAndDateAndStartTime(city, date, starttime));
            else
                list1.add(new CurrentHourStatusEntity("not available", city, date, starttime, 0.0f, 0.0f, 0.0f, "not available",
                        "/assets/gif/na.gif",
                        "/assets/na.webp"));
        }
        return  list1;
    }
    //function to get the cities in database.
    public  List<String> getCity() {
        ArrayList<String> city = new ArrayList<>();
        for (CurrentHourStatusEntity current : currentHourStatusRepo.findAll()) {
            if (!city.contains(current.getCity()))
                city.add(current.getCity());

        }
        return city;
    }



//function to calculate the average values for next six days.
    public CurrentWeekStatus getAveragefornextsixdays(List<CurrentHourStatusEntity> week){
        int length=0;
        float Pressure = 0,Temperature=0,Humidity=0,maxTemperature=0,minTemperature=0;
        String city="not available",date="not availble",weathertype="not available";
        String image[]=null;
        CurrentWeekStatus filterrecord=new CurrentWeekStatus();
        for (CurrentHourStatusEntity current : week) {
            city=current.getCity();
            date=current.getDate();
            minTemperature = current.getTemperature();
            break;
        }
        for (CurrentHourStatusEntity current : week) {
            length++;
            Pressure += current.getPrecipitation();
            Temperature += current.getTemperature();
            Humidity += current.getHumidity();
            if (maxTemperature < current.getTemperature())
                maxTemperature = current.getTemperature();
            if (minTemperature > current.getTemperature())
                minTemperature = current.getTemperature();
        }
        filterrecord.setCity(city);
        filterrecord.setDate(date);
        filterrecord.setAvgHumidity(Humidity/length);
        filterrecord.setAvgPrecipitation(Pressure/length);
        filterrecord.setAvgTemperature(Temperature/length);
        filterrecord.setMaxTemperature(maxTemperature);
        filterrecord.setMinTemperature(minTemperature);
        weathertype=setWeather(Pressure/length,19,Temperature/length);
        filterrecord.setWeatherType(weathertype);
        image=getimages(weathertype);
        filterrecord.setWeathergif(image[0]);
        filterrecord.setWeatherimage(image[1]);
        return filterrecord;
    }

//function to get the error status
    public ReturnAfterSavingData geterrorcode(CurrentHourStatusEntity data)
    {
        if(data.getCity() == null)
            return new ReturnAfterSavingData(-1 , null);
        else if (data.getStartTime() < 0 )
            return new ReturnAfterSavingData(-2, null);
        else if (isDate(data.getDate(), "yyyy-MM-dd", true) == false)
            return new ReturnAfterSavingData(-3, null);
        else if (data.getHumidity() == null)
            return new ReturnAfterSavingData(-4, null);
        else if (data.getHumidity() < 0 || data.getHumidity() > 100)
            return new ReturnAfterSavingData(-5, null);
        else if (data.getPrecipitation() == null)
            return new ReturnAfterSavingData(-6, null);
        else if (data.getPrecipitation() < 0 || data.getPrecipitation() > 100)
            return new ReturnAfterSavingData(-7, null);
        else if (data.getTemperature() == null)
            return new ReturnAfterSavingData(-8, null);
        else if(data.getCity().equals(""))
            return new ReturnAfterSavingData(-9, null);
        else if(!data.getCity().matches("[a-zA-Z]+"))
            return new ReturnAfterSavingData(-10 , null);
        else if (data.getStartTime() > 24)
            return new ReturnAfterSavingData(-11, null);
        else
            return new ReturnAfterSavingData(1, data);
    }
    public String[] getimages(String weathertype)
    {
        String image[] = new String[10];
        switch (weathertype) {
            case "sunny":
                image[0] = "/assets/gif/sunny.gif";
                image[1] = "/assets/sunny.png";
                break;
            case "partially cloudy":
                image[0] = "/assets/gif/partialcloudy.gif";
                image[1] = "/assets/partialclouds.png";
                break;
            case "sunny & clouds":
                image[0] = "/assets/gif/sunclouds.webp";
                image[1] = "/assets/sunnycloudy.webp";
                break;
            case "mostly cloudy":
                image[0] = "/assets/gif/cloudynight.gif";
                image[1] = "/assets/mostlyclouds.png";
                break;
            case "sun showers":
                image[0] = "/assets/gif/sunshowers.gif";
                image[1] = "/assets/sun showers.png";
                break;
            case "fog":
                image[0] = "/assets/gif/fog.gif";
                image[1] = "/assets/fog.png";
                break;
            case "clear":
                image[0] = "/assets/gif/clearnight.gif";
                image[1] = "/assets/clearnight.webp";
                break;
            case "haze":
                image[0] = "/assets/gif/haze.gif";
                image[1] = "/assets/haze.png";
                break;
            case "mist":
                image[0] = "/assets/gif/mist.webp";
                image[1] = "/assets/mist.png";
                break;
            case "cloudy":
                image[0] = "/assets/gif/cloudy.gif";
                image[1] = "/assets/cloudy.jpeg";
                break;
            case "snow hail":
                image[0] = "/assets/gif/snowhail.gif";
                image[1] = "/assets/snowhail.webp";
                break;
            case "drizzle":
                image[0] = "/assets/gif/drizzle.gif";
                image[1] = "/assets/drizzle.webp";
                break;
            case "cloudy showers":
                image[0] = "/assets/gif/cloudyrain.gif";
                image[1] = "/assets/rain.png";
                break;
            case "showers":
                image[0] = "/assets/gif/nightshowers.gif";
                image[1] = "/assets/showers.webp";
                break;
            case "clouds & thunder":
                image[0] = "/assets/gif/cloudsthunder.webp";
                image[1] = "/assets/cloudthunder.webp";
                break;
            case "snowsquall":
                image[0] = "/assets/gif/sunny.gif";
                image[1] = "/assets/snowsquall.png";
                break;
            case "rain":
                image[0] = "/assets/gif/raingif.jpeg";
                image[1] = "/assets/rain.png";
                break;
            case "blizzard":
                image[0] = "/assets/gif/blizzard.gif";
                image[1] = "/assets/blizzard.png";
                break;
            case "heavy rain":
                image[0] = "/assets/gif/heavyrain.gif";
                image[1] = "/assets/heavyrain.png";
                break;
            case "snow flurry":
                image[0] = "/assets/gif/snowflurry.gif";
                image[1] = "/assets/snowflurry.png";
                break;
            case "thunder & rain":
                image[0] = "/assets/gif/thunderrain.gif";
                image[1] = "/assets/thunderrain.png";
                break;
            case "thundersnow":
                image[0] = "/assets/gif/thundersnow.gif";
                image[1] = "/assets/thundersnow.webp";
                break;
            case "storm":
                image[0] = "/assets/gif/storm.gif";
                image[1] = "/assets/storm.png";
                break;
            case "wintersnow":
                image[0] = "/assets/gif/wintersnow.gif";
                image[1] = "/assets/wintersnow.webp";
                break;
            case "thunderstorm":
                image[0] = "/assets/gif/thunderstorm.webp";
                image[1] = "/assets/thunderstorm.png";
                break;
            case "snowstorm":
                image[0] = "/assets/gif/snowstorm.gif";
                image[1] = "/assets/snowstorm.webp";
                break;
            default:
                image[0] = "/assets/gif/na.gif";
                image[1] = "/assets/na.webp";
                break;
        }
        return image;
    }
//function to set weather type on based on temperature,time,precipitation
    public String setWeather(float precipitation,int time,float temperature)
    {
        if( precipitation>=0&&precipitation<=6)
        {
            if(time>6&&time<18) {
                if (precipitation == 0 && temperature > 4)
                    return "sunny";
                else if (precipitation == 0 && temperature < 4)
                    return "partially cloudy";

                else if (precipitation > 0 && precipitation <= 4 && temperature > 4)
                    return "sunny & clouds";
                else if (precipitation > 0 && precipitation <= 4 && temperature < 4)
                    return "mostly cloudy";

                else if (precipitation > 4 && precipitation <= 6 && temperature > 4)
                    return "sun showers";
                else if (precipitation > 4 && precipitation <= 6 && temperature < 4)
                    return "fog";
                else
                    return "not available";
            }
            else
            {
                if (precipitation == 0)
                return "clear";
            else if (precipitation > 0 && precipitation <= 4 && temperature > 4)
                return "partially cloudy";
            else if (precipitation > 0 && precipitation <= 4 && temperature < 4)
                return "partially cloudy";

            else if (precipitation > 4 && precipitation <= 6 && temperature > 4)
                return "showers";
            else if (precipitation > 4 && precipitation <= 6 && temperature < 4)
                return "fog";
            else
                return "not available";
            }
        }
        else if(precipitation>6&&precipitation<=10)
        {
            if(temperature>4)
                return "haze";
            else if(temperature<4)
                return "mist";
            else
                return "not available";
        }
        else if(precipitation>10&&precipitation<=15)
        {
            if(temperature>4)
                return "cloudy";
            else if(temperature<4)
                return "cloudy";
            else
                return "not available";
        }
        else if(precipitation>15&&precipitation<=18)
        {
            if(temperature>4)
                return "mist";
            else if(temperature<4)
                return "snow hail";
            else
                return "not available";
        }

        else if(precipitation>18&&precipitation<=22)
        {
            if(temperature>4)
                return "drizzle";
            else if(temperature<4)
                return "cloudy showers";
            else
                return "not available";
        }
        else if(precipitation>22&&precipitation<=28)
        {
            if(temperature>4)
                return "clouds & thunder";
            else if(temperature<4)
                return "snowsquall";
            else
                return "not available";
        }
        else if(precipitation>28&&precipitation<=45)
        {
            if(temperature>4)
                return "rain";
            else if(temperature<4)
                return "blizzard";
            else
                return "not available";
        }
        else if(precipitation>45&&precipitation<=65)
        {
            if(temperature>4)
                return "heavy rain";
            else if(temperature<4)
                return "snow flurry";
            else
                return "not available";
        }
        else if(precipitation>65&&precipitation<=80)
        {
            if(temperature>4)
                return "thunder & rain";
            else if(temperature<4)
                return "thundersnow";
            else
                return "not available";
        }
        else if(precipitation>80&&precipitation<=90)
        {
            if(temperature>4)
                return "storm";
            else if(temperature<4)
                return "wintersnow";
            else
                return "not available";
        }
        else if(precipitation>90&&precipitation<=100)
        {
            if(temperature>4)
                return "thunderstorm";
            else if(temperature<4)
                return "snowstorm";
            else
                return "not available";
        }
        else {
            return "not available";
        }
    }
}
