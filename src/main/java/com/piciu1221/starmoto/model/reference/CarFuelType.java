package com.piciu1221.starmoto.model.reference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "car_fuel_type")
public class CarFuelType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fuel_type_id")
    private Integer fuelTypeId;

    @Column(name = "fuel_type_name", nullable = false, unique = true)
    private String fuelTypeName;
}
