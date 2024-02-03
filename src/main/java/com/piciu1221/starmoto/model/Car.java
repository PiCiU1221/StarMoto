package com.piciu1221.starmoto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @Column(name = "vin", unique = true, nullable = false, length = 17)
    private String vin;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "make_id", nullable = false)
    private CarMake make;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private CarModel model;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "body_type_id", nullable = false)
    private CarBodyType bodyType;

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
    @JoinColumn(name = "transmission_type_id", nullable = false)
    private CarTransmissionType transmissionType;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "drivetrain_id", nullable = false)
    private CarDrivetrainType drivetrainType;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "doors_id", nullable = false)
    private CarDoorCount doors;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "seats_id", nullable = false)
    private CarSeatCount seats;

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

    @Min(value = 1, message = "Engine power should be at least 1")
    @Column(name = "engine_power")
    private Integer enginePower;

    @Min(value = 1, message = "Engine capacity should be at least 1")
    @Column(name = "engine_capacity")
    private Integer engineCapacity;

    @Column(name = "is_damaged")
    private Boolean isDamaged;

    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt = null;

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
            name = "car_image_urls_junction",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id")
    )
    private List<CarImageUrl> imageUrls;
}