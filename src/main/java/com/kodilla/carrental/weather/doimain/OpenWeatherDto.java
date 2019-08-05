package com.kodilla.carrental.weather.doimain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherDto {

    @JsonProperty("city")
    private CityDto cityDto;

    @JsonProperty("list")
    private List<ListDto> listDto = new ArrayList<>();


}

