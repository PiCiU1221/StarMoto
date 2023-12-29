package com.piciu1221.starmoto.model;

import com.piciu1221.starmoto.model.reference.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

    @NotNull
    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private CarModel model;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private CarType type;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "condition_id", nullable = false)
    private CarCondition condition;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private CarColor color;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "fuel_type_id", nullable = false)
    private CarFuelType fuelType;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "transmission_id", nullable = false)
    private CarTransmissionType transmissionType;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "doors_id", nullable = false)
    private CarDoorCount doors;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "seats_id", nullable = false)
    private CarSeatCount seats;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "drivetrain_id", nullable = false)
    private CarDrivetrainType drivetrainType;

    @Min(value = 1900, message = "Production year should be at least 1900")
    @Column(name = "production_year")
    private Integer productionYear;

    @Min(value = 1, message = "Price should be greater than 0")
    @Max(value = 99999999, message = "Price should not exceed 99999999")
    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Min(value = 0, message = "Mileage cannot be negative")
    @Column(name = "mileage")
    private Integer mileage;

    @Min(value = 1, message = "Horsepower should be at least 1")
    @Column(name = "horsepower")
    private Integer horsepower;

    @Min(value = 1, message = "Engine capacity should be at least 1")
    @Column(name = "engine_capacity")
    private Integer engineCapacity;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Size(max = 7, message = "Up to 7 features are allowed")
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "car_features_junction",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    private List<CarFeature> features;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "car_images_junction",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<CarImage> images;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "active")
    private Boolean active = true;
}