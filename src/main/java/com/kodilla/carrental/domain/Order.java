package com.kodilla.carrental.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CAR_ORDER")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ID")
    private Long id;

    @Column(name = "ORDER_NUMBER")
    private String orderNumber;

    @Column(name = "DATE_OF_ORDER")
    @NotNull
    private LocalDate dateOfOrder;

    @Column(name = "DATE_OF_CAR_RENTAL")
    @NotNull
    private LocalDate dateOfCarRental;

    @Column(name = "DATE_OF_RETURN_CAR")
    @NotNull
    private LocalDate dateOfReturnCar;

    @Column(name = "STATUS_OF_ORDER")
    @NotNull
    private boolean statusOrder;

    @Column(name = "PRIZE")
    private double prize;

    @Column(name = "EQUIPMENTS")
    private String equipments;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    @NotNull
    private User user;

    @OneToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "CAR_ID")
    private Car car;

    public Order(LocalDate dateOfCarRental, LocalDate dateOfReturnCar, User user, Car car) {
        this.dateOfCarRental = dateOfCarRental;
        this.dateOfReturnCar = dateOfReturnCar;
        this.user = user;
        this.car = car;
        this.dateOfOrder = LocalDate.now();
        this.statusOrder = false;
    }
}
