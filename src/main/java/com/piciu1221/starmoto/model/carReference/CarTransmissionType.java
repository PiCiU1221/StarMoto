package com.piciu1221.starmoto.model.carReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Data
@Table(name = "car_transmission_types")
public class CarTransmissionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transmission_type_id")
    private Long transmissionTypeId;

    @Column(name = "transmission_type_name", nullable = false, unique = true)
    @NotBlank
    private String transmissionTypeName;
}
