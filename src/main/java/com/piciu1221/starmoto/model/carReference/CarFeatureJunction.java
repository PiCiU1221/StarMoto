package com.piciu1221.starmoto.model.carReference;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "car_features_junction")
public class CarFeatureJunction {
    @EmbeddedId
    private CarFeatureJunctionId id;
}
