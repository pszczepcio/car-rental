package com.kodilla.carrental.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDtoList {
        private Long id;
        private String name;
        private String surname;
        private int phone;
        private String email;
        private boolean loginStatus;
    }
