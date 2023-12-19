package com.piciu1221.starmoto.model;

import com.piciu1221.starmoto.model.reference.*;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Integer carId;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private CarModel model;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private CarType type;

    @ManyToOne
    @JoinColumn(name = "condition_id", nullable = false)
    private CarCondition condition;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private CarColor color;

    @ManyToOne
    @JoinColumn(name = "fuel_type_id", nullable = false)
    private CarFuelType fuelType;

    @ManyToOne
    @JoinColumn(name = "transmission_id", nullable = false)
    private CarTransmissionType transmissionType;

    @ManyToOne
    @JoinColumn(name = "doors_id", nullable = false)
    private CarDoorCount doors;

    @ManyToOne
    @JoinColumn(name = "seats_id", nullable = false)
    private CarSeatCount seats;

    @ManyToOne
    @JoinColumn(name = "drivetrain_id", nullable = false)
    private CarDrivetrainType drivetrainType;

    @Column(name = "production_year")
    private Integer productionYear;

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "mileage")
    private Integer mileage;

    @Column(name = "horsepower")
    private Integer horsepower;

    @Column(name = "engine_capacity")
    private Integer engineCapacity;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "active")
    private Boolean active = true;

    @ManyToMany
    @JoinTable(
            name = "car_features_junction",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private List<CarFeature> features;

    @ManyToMany
    @JoinTable(
            name = "car_images_junction",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<CarImage> images;
}
