package com.piciu1221.starmoto.model.carReference;

import com.piciu1221.starmoto.model.Car;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class CarFeatureJunctionId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    @NotNull
    private Car car;

    @ManyToOne
    @JoinColumn(name = "feature_id", nullable = false)
    @NotNull
    private CarFeature feature;
}
