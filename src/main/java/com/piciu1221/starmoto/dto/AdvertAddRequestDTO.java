package com.piciu1221.starmoto.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class AdvertAddRequestDTO {
    @NotNull(message = "Seller ID is required")
    @Positive(message = "Seller ID must be a positive integer")
    private Long sellerId;

    @NotNull(message = "Title is required")
    @Size(min = 5, message = "Title must be at least 5 characters long")
    private String title;

    @NotNull(message = "Description is required")
    @Size(min = 10, message = "Description must be at least 10 characters long")
    private String description;

    @NotNull(message = "Location ID is required")
    @Positive(message = "Location ID must be a positive integer")
    private Long locationId;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive integer")
    private Integer price;

    @Size(min = 1, message = "At least one phone number is required")
    private List<String> phoneNumbers;

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
}