package com.piciu1221.starmoto.model.reference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "car_drivetrain_type")
public class CarDrivetrainType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "drivetrain_type_id")
    private Integer drivetrainId;

    @Column(name = "drivetrain_type_name", nullable = false, unique = true)
    private String drivetrainName;
}
