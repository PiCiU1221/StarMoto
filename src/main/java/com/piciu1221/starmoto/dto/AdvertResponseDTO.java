package com.piciu1221.starmoto.dto;

import com.piciu1221.starmoto.model.Advert;
import com.piciu1221.starmoto.model.advertReference.AdvertPhoneNumber;
import com.piciu1221.starmoto.model.carReference.CarFeature;
import com.piciu1221.starmoto.model.carReference.CarImageUrl;
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

        this.carInfo = new CarInfo();
        this.carInfo.setVin(advert.getCar().getVin());
        this.carInfo.setMake(advert.getCar().getMake().getMakeName());
        this.carInfo.setModel(advert.getCar().getModel().getModelName());
        this.carInfo.setBodyType(advert.getCar().getBodyType().getBodyTypeName());
        this.carInfo.setColor(advert.getCar().getColor().getColorName());
        this.carInfo.setFuelType(advert.getCar().getFuelType().getFuelTypeName());
        this.carInfo.setTransmissionType(advert.getCar().getTransmissionType().getTransmissionTypeName());
        this.carInfo.setDrivetrainType(advert.getCar().getDrivetrainType().getDrivetrainTypeName());
        this.carInfo.setDoorsCount(advert.getCar().getDoors().getDoorCount());
        this.carInfo.setSeatsCount(advert.getCar().getSeats().getSeatsCount());
        this.carInfo.setProductionYear(advert.getCar().getProductionYear());
        this.carInfo.setMileage(advert.getCar().getMileage());
        this.carInfo.setEnginePower(advert.getCar().getEnginePower());
        this.carInfo.setEngineCapacity(advert.getCar().getEngineCapacity());
        this.carInfo.setIsDamaged(advert.getCar().getIsDamaged());

        // Car features
        List<CarFeature> carFeatures = advert.getCar().getFeatures();
        List<String> features = new ArrayList<>();

        for (CarFeature feature : carFeatures) {
            features.add(feature.getFeatureName());
        }

        this.carInfo.setFeatures(features);

        // Car imagesUrls
        List<CarImageUrl> carImages = advert.getCar().getImages();
        List<String> images = new ArrayList<>();

        for (CarImageUrl image : carImages) {
            images.add(image.getImageUrl());
        }

        this.carInfo.setImageUrls(images);
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
        private List<String> imageUrls;
    }
}
