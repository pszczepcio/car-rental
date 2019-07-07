package com.kodilla.carrental.domain;

import javax.persistence.*;

@Entity
@Table(name = "INVOICE")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "INVOICE_NUMBER")
    private String invoiceNumber;
}
