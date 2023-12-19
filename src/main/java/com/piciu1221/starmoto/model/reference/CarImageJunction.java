package com.piciu1221.starmoto.model.reference;

import com.piciu1221.starmoto.model.Car;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "car_images_junction")
public class CarImageJunction {
    @Id
    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Id
    @ManyToOne
    @JoinColumn(name = "image_id", nullable = false)
    private CarImage image;
}
