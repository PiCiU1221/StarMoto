package com.piciu1221.starmoto.model.carReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "car_drivetrain_types")
public class CarDrivetrainType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drivetrain_type_id")
    @NotNull
    private Integer drivetrainTypeId;

    @Column(name = "drivetrain_type_name", nullable = false, unique = true)
    @NotBlank
    private String drivetrainTypeName;
}
