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
}
