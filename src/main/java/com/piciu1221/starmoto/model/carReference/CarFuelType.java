package com.piciu1221.starmoto.model.carReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "car_fuel_types")
public class CarFuelType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fuel_type_id")
    @NotNull
    private Integer fuelTypeId;

    @Column(name = "fuel_type_name", nullable = false, unique = true)
    @NotBlank
    private String fuelTypeName;
}
