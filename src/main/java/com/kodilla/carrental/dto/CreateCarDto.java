package com.kodilla.carrental.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCarDto {
    private Long id;
    private String carClass;
    private String typeOfCar;
    private String producer;
    private String model;
    private LocalDate dayOfProduction;
    private double pricePerDay;
    private String color;
    private int numberOfSeats;
    private boolean availability;
    private List<Long> equipmentIdDtoList;

//    public CreateCarDto(String carClass, String typeOfCar, String producer,
//                        String model, LocalDate dayOfProduction, double pricePerDay,
//                        String color, int numberOfSeats, boolean availability) {
//
//        this.carClass = carClass;
//        this.typeOfCar = typeOfCar;
//        this.producer = producer;
//        this.model = model;
//        this.dayOfProduction = dayOfProduction;
//        this.pricePerDay = pricePerDay;
//        this.color = color;
//        this.numberOfSeats = numberOfSeats;
//        this.availability = availability;
//        this.equipmentIdDtoList = new ArrayList<>();
//    }
}
