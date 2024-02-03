package com.piciu1221.starmoto.model.reference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "car_models")
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id")
    private Integer modelId;

    @Column(name = "model_name", nullable = false, length = 255)
    private String modelName;

    @ManyToOne
    @JoinColumn(name = "make_id", nullable = false)
    private CarMake makeId;
}