package com.piciu1221.starmoto.model.reference;

import com.piciu1221.starmoto.model.Car;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class CarFeatureJunctionId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @ManyToOne
    @JoinColumn(name = "feature_id", nullable = false)
    private CarFeature feature;
}
