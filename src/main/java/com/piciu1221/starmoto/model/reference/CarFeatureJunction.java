package com.piciu1221.starmoto.model.reference;

import com.piciu1221.starmoto.model.Car;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "car_features_junction")
public class CarFeatureJunction {
    @Id
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Id
    @ManyToOne
    @JoinColumn(name = "feature_id", nullable = false)
    private CarFeature feature;
}
