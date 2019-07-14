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
public class CreateUserDto {
    private Long id;
    private String name;
    private String surname;
    private int phone;
    private String eamil;
    private String password;
    private boolean loginStatus;
    private List<Long> orderIdList;
    private List<Long> invoiceIdList;

    public CreateUserDto(String name, String surname, int phone,
                         String eamil, String password) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.eamil = eamil;
        this.password = password;
        this.loginStatus = false;
        this.orderIdList = new ArrayList<>();
        this.invoiceIdList = new ArrayList<>();
    }
}
