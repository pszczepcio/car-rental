package com.kodilla.carrental.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarDto {
    private Long id;
    private String carClass;
    private String typeOfCar;
    private String producer;
    private String model;
    private Date dayOfProduction;
    private String equipment;
    private double pricePerDay;
    private String color;
    private int numberOfSeats;
    private boolean availability;
}
