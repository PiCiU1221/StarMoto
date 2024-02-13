package com.piciu1221.starmoto.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class CarAddRequestDTO {
    @NotNull(message = "VIN is required")
    @Size(min = 17, max = 17, message = "VIN must be 17 characters long")
    private String vin;

    @NotNull(message = "Make name is required")
    private String make;

    @NotNull(message = "Model name is required")
    private String model;

    @NotNull(message = "Body Type name is required")
    private String bodyType;

    @NotNull(message = "Color name is required")
    private String color;

    @NotNull(message = "Fuel Type name is required")
    private String fuelType;

    @NotNull(message = "Transmission name is required")
    private String transmissionType;

    @NotNull(message = "Drivetrain name is required")
    private String drivetrainType;

    @NotNull(message = "Doors count is required")
    private Integer doorsCount;

    @NotNull(message = "Seats count is required")
    private Integer seatsCount;

    @NotNull(message = "Production Year is required")
    @Min(value = 1900, message = "Production year should be at least 1900")
    private Integer productionYear;

    @NotNull(message = "Mileage is required")
    @Min(value = 0, message = "Mileage cannot be negative")
    private Integer mileage;

    @NotNull(message = "Engine power is required")
    @Min(value = 1, message = "Engine power should be at least 1")
    private Integer enginePower;

    @NotNull(message = "Engine Capacity is required")
    @Min(value = 1, message = "Engine capacity should be at least 1")
    private Integer engineCapacity;

    @NotNull(message = "Damage status is required")
    private Boolean isDamaged;

    @Size(max = 7, message = "Up to 7 features are allowed")
    private List<String> features;

    @NotNull(message = "Images are required")
    @NotEmpty(message = "Images list cannot be empty")
    private List<MultipartFile> images;

    public CarAddRequestDTO(AdvertAddRequestDTO advertAddRequestDTO) {
        this.vin = advertAddRequestDTO.getVin();
        this.make = advertAddRequestDTO.getMake();
        this.model = advertAddRequestDTO.getModel();
        this.bodyType = advertAddRequestDTO.getBodyType();
        this.color = advertAddRequestDTO.getColor();
        this.fuelType = advertAddRequestDTO.getFuelType();
        this.transmissionType = advertAddRequestDTO.getTransmissionType();
        this.drivetrainType = advertAddRequestDTO.getDrivetrainType();
        this.doorsCount = advertAddRequestDTO.getDoorsCount();
        this.seatsCount = advertAddRequestDTO.getSeatsCount();
        this.productionYear = advertAddRequestDTO.getProductionYear();
        this.mileage = advertAddRequestDTO.getMileage();
        this.enginePower = advertAddRequestDTO.getEnginePower();
        this.engineCapacity = advertAddRequestDTO.getEngineCapacity();
        this.isDamaged = advertAddRequestDTO.getIsDamaged();

        this.features = advertAddRequestDTO.getFeatures();
        this.images = advertAddRequestDTO.getImages();
    }
}