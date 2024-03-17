package com.piciu1221.starmoto.dto;

import com.piciu1221.starmoto.model.Advert;
import com.piciu1221.starmoto.model.Car;
import com.piciu1221.starmoto.model.advertReference.AdvertPhoneNumber;
import com.piciu1221.starmoto.model.carReference.CarFeature;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AdvertResponseDTO {
    private Long advertId;
    private Long sellerId;
    private String title;
    private String description;
    private String city;
    private Integer price;

    private List<String> phoneNumbers;

    private CarInfo carInfo;

    public AdvertResponseDTO(Advert advert) {
        this.advertId = advert.getAdvertId();
        this.sellerId = advert.getSeller().getUserId();
        this.title = advert.getTitle();
        this.description = advert.getDescription();
        this.city = advert.getLocation().getCity();
        this.price = advert.getPrice();

        // Advert phone numbers
        List<AdvertPhoneNumber> AdvertPhoneNumbers = advert.getPhoneNumbers();
        List<String> phoneNumbers = new ArrayList<>();

        for (AdvertPhoneNumber phoneNumber : AdvertPhoneNumbers) {
            phoneNumbers.add(phoneNumber.getPhoneNumber());
        }

        this.phoneNumbers = phoneNumbers;

        Car car = advert.getCar();

        this.carInfo = new CarInfo();
        this.carInfo.setVin(car.getVin());
        this.carInfo.setMake(car.getMake().getMakeName());
        this.carInfo.setModel(car.getModel().getModelName());
        this.carInfo.setBodyType(car.getBodyType().getBodyTypeName());
        this.carInfo.setColor(car.getColor().getColorName());
        this.carInfo.setFuelType(car.getFuelType().getFuelTypeName());
        this.carInfo.setTransmissionType(car.getTransmissionType().getTransmissionTypeName());
        this.carInfo.setDrivetrainType(car.getDrivetrainType().getDrivetrainTypeName());
        this.carInfo.setDoorsCount(car.getDoors().getDoorCount());
        this.carInfo.setSeatsCount(car.getSeats().getSeatsCount());
        this.carInfo.setProductionYear(car.getProductionYear());
        this.carInfo.setMileage(car.getMileage());
        this.carInfo.setEnginePower(car.getEnginePower());
        this.carInfo.setEngineCapacity(car.getEngineCapacity());
        this.carInfo.setIsDamaged(car.getIsDamaged());

        // Car features
        List<CarFeature> carFeatures = car.getFeatures();
        List<String> features = new ArrayList<>();

        for (CarFeature feature : carFeatures) {
            features.add(feature.getFeatureName());
        }

        this.carInfo.setFeatures(features);

        this.carInfo.setImageCollectionId(car.getImageCollection().getCollectionId());
    }

    @Data
    public static class CarInfo {
        private String vin;
        private String make;
        private String model;
        private String bodyType;
        private String color;
        private String fuelType;
        private String transmissionType;
        private String drivetrainType;
        private Integer doorsCount;
        private Integer seatsCount;
        private Integer productionYear;
        private Integer mileage;
        private Integer enginePower;
        private Integer engineCapacity;
        private Boolean isDamaged;
        private List<String> features;
        private Long imageCollectionId;
    }
}
