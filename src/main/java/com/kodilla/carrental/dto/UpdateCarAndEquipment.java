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
public class UpdateCarAndEquipment {
    private List<Long> eguipmentIdList = new ArrayList<>();
    private Long carId;
}