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
public class CreateOrderDto {
    private LocalDate dateOfCarRental;
    private LocalDate dateOfReturnCar;
    private Long userId;
    private Long carId;
    private String equipments;
}
