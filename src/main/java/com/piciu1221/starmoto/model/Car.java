package com.piciu1221.starmoto.model;

import com.piciu1221.starmoto.model.carReference.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long carId;

    @Column(name = "vin", unique = true, nullable = false, length = 17)
    @NotBlank
    @Size(min = 17, max = 17)
    private String vin;

    @ManyToOne
    @JoinColumn(name = "make_id", nullable = false)
    @NotNull
    private CarMake make;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    @NotNull
    private CarModel model;

    @ManyToOne
    @JoinColumn(name = "body_type_id", nullable = false)
    @NotNull
    private CarBodyType bodyType;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    @NotNull
    private CarColor color;

    @ManyToOne
    @JoinColumn(name = "fuel_type_id", nullable = false)
    @NotNull
    private CarFuelType fuelType;

    @ManyToOne
    @JoinColumn(name = "transmission_type_id", nullable = false)
    @NotNull
    private CarTransmissionType transmissionType;

    @ManyToOne
    @JoinColumn(name = "drivetrain_id", nullable = false)
    @NotNull
    private CarDrivetrainType drivetrainType;

    @ManyToOne
    @JoinColumn(name = "doors_id", nullable = false)
    @NotNull
    private CarDoorCount doors;

    @ManyToOne
    @JoinColumn(name = "seats_id", nullable = false)
    @NotNull
    private CarSeatCount seats;

    @Column(name = "production_year", nullable = false)
    @NotNull
    @Min(value = 1900, message = "Production year should be at least 1900")
    private Integer productionYear;

    @Column(name = "mileage", nullable = false)
    @NotNull
    @Min(value = 0, message = "Mileage cannot be negative")
    private Integer mileage;

    @Column(name = "engine_power", nullable = false)
    @NotNull
    @Min(value = 1, message = "Engine power should be at least 1")
    private Integer enginePower;

    @Column(name = "engine_capacity", nullable = false)
    @NotNull
    @Min(value = 1, message = "Engine capacity should be at least 1")
    private Integer engineCapacity;

    @Column(name = "is_damaged", nullable = false)
    @NotNull
    private Boolean isDamaged;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "car_features_junction",
            joinColumns = @JoinColumn(name = "car_id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id")
    )
    @Size(max = 7, message = "Up to 7 features are allowed")
    private List<CarFeature> features;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "car_id")
    private List<CarImageUrl> images;
}