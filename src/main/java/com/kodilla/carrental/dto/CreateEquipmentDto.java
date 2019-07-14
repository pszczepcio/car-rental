package com.kodilla.carrental.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEquipmentDto {
    private Long id;
    private String equipment;
    private double prize;
    private List<Long> carid = new ArrayList<>();

    public CreateEquipmentDto(String equipment, double prize, List<Long> carid) {
        this.equipment = equipment;
        this.prize = prize;
        this.carid = carid;
    }
}
