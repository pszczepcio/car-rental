package com.kodilla.carrental.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "CAR_ORDER")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ORDER_NUMBER")
    @NotNull
    private String orderNumber;

    @Column(name = "DATE_OF_ORDER")
    @NotNull
    private Date dateOfOrder;

    @Column(name = "DATE_OF_CAR_RENTAL")
    @NotNull
    private Date dateOfCarRental;

    @Column(name = "DATE_OF_RETURN_CAR")
    @NotNull
    private Date dateOfReturnCar;

    @Column(name = "STATUS_OF_ORDER")
    @NotNull
    private boolean statusOrder;

    @Column(name = "PRIZE")
    @NotNull
    private Long prize;
}
