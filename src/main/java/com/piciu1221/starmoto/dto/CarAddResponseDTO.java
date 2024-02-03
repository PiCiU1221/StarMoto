package com.piciu1221.starmoto.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.piciu1221.starmoto.model.Car;
import com.piciu1221.starmoto.model.reference.CarFeature;
import com.piciu1221.starmoto.model.reference.CarImageUrl;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class CarAddResponseDTO {

    private Integer carId;
    private Integer sellerId;
    private String vin;
    private String make;
    private String model;
    private String bodyType;
    private String color;
    private String fuelType;
    private String transmissionType;
    private String drivetrainType;
    private Integer doors;
    private Integer seats;
    private Integer productionYear;
    private BigDecimal price;
    private Integer mileage;
    private Integer enginePower;
    private Integer engineCapacity;
    private Boolean isDamaged;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt;
    
    private List<String> features;
    private List<String> imageUrls;

    public CarAddResponseDTO(Car car) {
        this.carId = car.getCarId();
        this.sellerId = car.getSeller().getId();
        this.vin = car.getVin();
        this.make = car.getMake().getMakeName();
        this.model = car.getModel().getModelName();
        this.bodyType = car.getBodyType().getBodyTypeName();
        this.color = car.getColor().getColorName();
        this.fuelType = car.getFuelType().getFuelTypeName();
        this.transmissionType = car.getTransmissionType().getTransmissionTypeName();
        this.drivetrainType = car.getDrivetrainType().getDrivetrainTypeName();
        this.doors = car.getDoors().getDoorCount();
        this.seats = car.getSeats().getSeatsCount();
        this.productionYear = car.getProductionYear();
        this.price = car.getPrice();
        this.mileage = car.getMileage();
        this.enginePower = car.getEnginePower();
        this.engineCapacity = car.getEngineCapacity();
        this.isDamaged = car.getIsDamaged();
        this.createdAt = car.getCreatedAt();
        this.updatedAt = car.getUpdatedAt();

        // Car features
        List<CarFeature> carFeatures = car.getFeatures();
        List<String> features = new ArrayList<>();

        for (CarFeature feature : carFeatures) {
            features.add(feature.getFeatureName());
        }

        this.features = features;

        // Car imagesUrls
        List<CarImageUrl> carImages = car.getImageUrls();
        List<String> images = new ArrayList<>();

        for (CarImageUrl image : carImages) {
            images.add(image.getImageUrl());
        }

        this.imageUrls = images;
    }
}