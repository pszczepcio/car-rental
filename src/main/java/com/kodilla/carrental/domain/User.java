package com.kodilla.carrental.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USER_NAME")
    @NotNull
    private String name;

    @Column(name = "USER_SURNAME")
    @NotNull
    private String surname;

    @Column(name = "PHONE")
    @NotNull
    private int phone;

    @Column(name = "E_MAIL")
    @NotNull
    private String eamil;

    @Column(name = "USER_PASSWORD")
    @NotNull
    private String password;

    @Column(name = "STATUS")
    private boolean loginStatus;
}
