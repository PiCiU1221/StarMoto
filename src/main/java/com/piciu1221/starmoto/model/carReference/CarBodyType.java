package com.piciu1221.starmoto.model.carReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "car_body_types")
public class CarBodyType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    @Column(name = "body_type_id")
    private Integer bodyTypeId;

    @Column(name = "body_type_name", nullable = false)
    @NotNull
    private String bodyTypeName;
}
