package com.piciu1221.starmoto.model.reference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "car_body_types")
public class CarBodyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "body_type_id")
    private Integer bodyTypeId;

    @Column(name = "body_type_name", nullable = false, length = 255)
    private String bodyTypeName;
}
