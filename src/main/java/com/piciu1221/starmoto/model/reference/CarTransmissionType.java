package com.piciu1221.starmoto.model.reference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "car_transmission_types")
public class CarTransmissionType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transmission_type_id")
    private Integer transmissionTypeId;

    @Column(name = "transmission_type_name", nullable = false, unique = true)
    private String transmissionTypeName;
}
