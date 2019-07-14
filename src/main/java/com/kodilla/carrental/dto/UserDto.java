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
public class UserDto {
    private Long id;
    private String name;
    private String surname;
    private int phone;
    private String eamil;
    private boolean loginStatus;
    private List<Long> orderIdList = new ArrayList<>();
    private List<Long> invoiceIdList = new ArrayList<>();

    public UserDto(Long id, String name, String surname, int phone, String eamil, boolean loginStatus) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.eamil = eamil;
        this.loginStatus = loginStatus;
    }
}
