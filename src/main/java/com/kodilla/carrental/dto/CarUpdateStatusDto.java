package com.kodilla.carrental.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarUpdateStatusDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("availability")
    private boolean availability;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class WeatherDto {
        private String cod;
        private String message;
        private String cnt;
    }
}
