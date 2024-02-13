package com.piciu1221.starmoto.model.carReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "car_features")
public class CarFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feature_id")
    private Long featureId;

    @Column(name = "feature_name", nullable = false, unique = true, length = 50)
    @NotBlank
    @Size(max = 50)
    private String featureName;
}
