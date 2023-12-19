package com.piciu1221.starmoto.model.reference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "car_features")
public class CarFeature {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feature_id")
    private Integer featureId;

    @Column(name = "feature_name", nullable = false, unique = true, length = 50)
    private String featureName;
}
