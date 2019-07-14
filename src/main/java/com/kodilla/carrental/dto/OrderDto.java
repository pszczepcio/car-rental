package com.kodilla.carrental.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
}
