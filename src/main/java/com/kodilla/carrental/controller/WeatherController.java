package com.kodilla.carrental.controller;

import com.kodilla.carrental.weather.doimain.OpenWeatherDto;
import com.kodilla.carrental.openweather.OpenWeatherClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/weather")
public class WeatherController {

    @Autowired
    private OpenWeatherClient openWeatherClient;

    @RequestMapping(method = RequestMethod.GET, value = "/getWeather")
    public OpenWeatherDto getWeather() {
      return openWeatherClient.getWeatherFor5Days();
    }
}
