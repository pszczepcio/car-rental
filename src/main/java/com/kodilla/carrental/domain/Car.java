package com.kodilla.carrental.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
//@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CAR")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CAR_CLASS")
    @NotNull
    private String carClass;

    @Column(name = "TYPE_OF_CAR")
    @NotNull
    private String typeOfCar;

    @Column(name = "PRODUCER")
    @NotNull
    private String producer;

    @Column(name = "MODEL")
    @NotNull
    private String model;

    @Column(name = "DAY_OF_PRODUCTION")
    @NotNull
    private Date dayOfProduction;

    @Column(name = "EQUIPMENT")
    @NotNull
    private String equipment;

    @Column(name = "PRICE_PER_DAY")
    @NotNull
    private double pricePerDay;

    @Column(name = "COLOR")
    @NotNull
    private String color;

    @Column(name = "NUMBER_OF_SEATS")
    @NotNull
    private int numberOfSeats;

    @Column(name = "AVAILABILITY")
    @NotNull
    private boolean availability;

    public Car(@NotNull String carClass, @NotNull String typeOfCar, @NotNull String producer, @NotNull String model, @NotNull Date dayOfProduction, @NotNull String equipment, @NotNull double pricePerDay, @NotNull String color, @NotNull int numberOfSeats, @NotNull boolean availability) {
        this.carClass = carClass;
        this.typeOfCar = typeOfCar;
        this.producer = producer;
        this.model = model;
        this.dayOfProduction = dayOfProduction;
        this.equipment = equipment;
        this.pricePerDay = pricePerDay;
        this.color = color;
        this.numberOfSeats = numberOfSeats;
        this.availability = availability;
    }
}
