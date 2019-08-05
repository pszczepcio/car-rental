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
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    private Long id;
    private String orderNumber;
    private LocalDate dateOfOrder;
    private LocalDate dateOfCarRental;
    private LocalDate dateOfReturnCar;
    private boolean statusOrder;
    private double prize;
    private Long userId;
    private Long carId;
    private String equipments;

//    public OrderDto(Long id, String orderNumber, LocalDate dateOfOrder,
//                    LocalDate dateOfCarRental, LocalDate dateOfReturnCar,
//                    boolean statusOrder, double prize, Long userId, Long carId) {
//        this.id = id;
//        this.orderNumber = orderNumber;
//        this.dateOfOrder = dateOfOrder;
//        this.dateOfCarRental = dateOfCarRental;
//        this.dateOfReturnCar = dateOfReturnCar;
//        this.statusOrder = statusOrder;
//        this.prize = prize;
//        this.userId = userId;
//        this.carId = carId;
//    }
}
