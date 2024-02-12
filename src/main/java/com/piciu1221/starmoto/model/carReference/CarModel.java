package com.piciu1221.starmoto.model.carReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "car_models",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"model_name", "make_id"})})
public class CarModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "model_id")
    @NotNull
    private Integer modelId;

    @Column(name = "model_name", nullable = false)
    @NotBlank
    private String modelName;

    @ManyToOne
    @JoinColumn(name = "make_id", nullable = false)
    @NotNull
    private CarMake makeId;
}