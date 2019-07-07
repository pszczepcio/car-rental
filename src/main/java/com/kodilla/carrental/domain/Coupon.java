package com.kodilla.carrental.domain;

import javax.persistence.*;

@Entity
@Table(name = "PROMOTIONAL_COUPON")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "COUPON_NUMBER")
    private String couponNumber;

}
