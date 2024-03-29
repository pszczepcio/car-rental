package com.kodilla.carrental.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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
    private String email;

    @Column(name = "USER_PASSWORD")
    @NotNull
    private String password;

    @Column(name = "STATUS")
    private boolean loginStatus;

    @OneToMany(
            targetEntity = Order.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Order> orderList = new ArrayList<>();

    @OneToMany(
            targetEntity = Invoice.class,
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Invoice> invoiceList = new ArrayList<>();

    public User(@NotNull String name, @NotNull String surname, @NotNull int phone,
                @NotNull String eamil, @NotNull String password) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.email = eamil;
        this.password = password;
        this.loginStatus = false;
        this.orderList = new ArrayList<>();
        this.invoiceList = new ArrayList<>();
    }
}
