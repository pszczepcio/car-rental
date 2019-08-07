package com.kodilla.carrental.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ADDITIONAL_EQUIPMENT")
public class AdditionalEquipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDITIONAL_EQUIPMENT_ID")
    private Long id;

    @Column(name = "EQUIPMENT")
    private String equipment;

    @Column(name = "PRIZE")
    private double prize;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "join_car_equipmnet",
            joinColumns = {@JoinColumn(name = "ADDITIONAL_EQUIPMENT_ID", referencedColumnName = "ADDITIONAL_EQUIPMENT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "CAR_ID", referencedColumnName = "CAR_ID")}
    )
    private List<Car> carsList = new ArrayList<>();

    public AdditionalEquipment(String equipment, double prize) {
        this.equipment = equipment;
        this.prize = prize;
        this.carsList = new ArrayList<>();
    }
}
