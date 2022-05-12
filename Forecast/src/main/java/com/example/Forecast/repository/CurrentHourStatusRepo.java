package com.example.Forecast.repository;
import com.example.Forecast.entity.CurrentHourStatusEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrentHourStatusRepo extends MongoRepository<CurrentHourStatusEntity,String> {
CurrentHourStatusEntity findFirstByCityAndDateAndStartTime(String city,String date, int time) ;

    List<CurrentHourStatusEntity> findByCityAndDate(String city, String date) ;


}
