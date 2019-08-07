package com.kodilla.carrental.openweather;


import com.kodilla.carrental.weather.doimain.OpenWeatherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class OpenWeatherClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenWeatherClient.class);

    @Value("${openWeather.api.endpoint.prod}")
    private String openWeatherApiEndpoint;

    @Value("${openWeather.api.key}")
    private String openWeatherKey;

    @Autowired
    RestTemplate restTemplate;

    public OpenWeatherDto getWeatherFor5Days() {
        try {
            OpenWeatherDto weatherResponse = restTemplate.getForObject(createUrl(), OpenWeatherDto.class);
            return weatherResponse;
        }catch (RestClientException e){
         LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    private URI createUrl() {
        URI url = UriComponentsBuilder.fromHttpUrl(openWeatherApiEndpoint)
                .queryParam("q", "Krosno,pl")
                .queryParam("units", "metric")
                .queryParam("appid", openWeatherKey).build().encode().toUri();
        return url;
    }
}
