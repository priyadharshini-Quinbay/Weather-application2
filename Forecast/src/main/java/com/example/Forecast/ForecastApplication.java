package com.example.Forecast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;


@EnableCaching
@SpringBootApplication
public class ForecastApplication {

	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(ForecastApplication.class);
		SpringApplication.run(ForecastApplication.class, args);
	}

}
