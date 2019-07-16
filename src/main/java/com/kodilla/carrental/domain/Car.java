package com.kodilla.carrental.domain;

import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CAR")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAR_ID")
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
    private LocalDate dayOfProduction;

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

    @ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "carsList")
    @Builder.Default
    private List<AdditionalEquipment> equipments = new ArrayList<>();

    public Car(String carClass, String typeOfCar, String producer,
               String model, LocalDate dayOfProduction, double pricePerDay,
               String color, int numberOfSeats, boolean availability) {
        this.carClass = carClass;
        this.typeOfCar = typeOfCar;
        this.producer = producer;
        this.model = model;
        this.dayOfProduction = dayOfProduction;
        this.pricePerDay = pricePerDay;
        this.color = color;
        this.numberOfSeats = numberOfSeats;
        this.availability = availability;
        this.equipments = new ArrayList<>();
    }

    public Car (Car car) {
        this.carClass = car.getCarClass();
        this.typeOfCar = car.getTypeOfCar();
        this.producer = car.getProducer();
        this.model = car.getModel();
        this.dayOfProduction = car.getDayOfProduction();
        this.pricePerDay = car.getPricePerDay();
        this.color = car.getColor();
        this.numberOfSeats = car.getNumberOfSeats();
        this.availability = car.isAvailability();
        List<AdditionalEquipment> equipmentList = new ArrayList<>();
        this.equipments = equipmentList;
    }
}
