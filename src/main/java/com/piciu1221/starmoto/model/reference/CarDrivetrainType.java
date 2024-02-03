package com.piciu1221.starmoto.model.reference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "car_drivetrain_types")
public class CarDrivetrainType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drivetrain_type_id")
    private Integer drivetrainTypeId;

    @Column(name = "drivetrain_type_name", nullable = false, unique = true)
    private String drivetrainTypeName;
}
