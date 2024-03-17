package com.piciu1221.starmoto.dto;

import com.piciu1221.starmoto.model.Car;
import com.piciu1221.starmoto.model.carReference.CarFeature;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CarAddResponseDTO {
    private Long carId;
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
    private Integer mileage;
    private Integer enginePower;
    private Integer engineCapacity;
    private Boolean isDamaged;

    private List<String> features;
    private Long imageCollectionId;

    public CarAddResponseDTO(Car car) {
        this.carId = car.getCarId();
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
        this.mileage = car.getMileage();
        this.enginePower = car.getEnginePower();
        this.engineCapacity = car.getEngineCapacity();
        this.isDamaged = car.getIsDamaged();

        // Car features
        List<CarFeature> carFeatures = car.getFeatures();
        List<String> features = new ArrayList<>();

        for (CarFeature feature : carFeatures) {
            features.add(feature.getFeatureName());
        }

        this.features = features;

        this.setImageCollectionId(car.getImageCollection().getCollectionId());
    }
}
