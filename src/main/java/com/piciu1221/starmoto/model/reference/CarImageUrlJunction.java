package com.piciu1221.starmoto.model.reference;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "car_image_urls_junction")
public class CarImageUrlJunction {

    @EmbeddedId
    private CarImageUrlJunctionId id;
}
