package com.piciu1221.starmoto.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CarAddRequestDTO {
    @NotNull(message = "VIN is required")
    @Size(min = 17, max = 17, message = "VIN must be 17 characters long")
    private String vin;

    @NotNull(message = "Seller ID is required")
    @Positive(message = "Seller ID must be a positive integer")
    private Long sellerId;

    @NotNull(message = "Make name is required")
    private String makeName;

    @NotNull(message = "Model name is required")
    private String modelName;

    @NotNull(message = "Body Type name is required")
    private String bodyTypeName;

    @NotNull(message = "Condition name is required")
    private String conditionName;

    @NotNull(message = "Color name is required")
    private String colorName;

    @NotNull(message = "Fuel Type name is required")
    private String fuelTypeName;

    @NotNull(message = "Transmission name is required")
    private String transmissionTypeName;

    @NotNull(message = "Drivetrain name is required")
    private String drivetrainTypeName;

    @NotNull(message = "Doors count is required")
    private Integer doorsCount;

    @NotNull(message = "Seats count is required")
    private Integer seatsCount;

    @NotNull(message = "Production Year is required")
    @Min(value = 1900, message = "Production year should be at least 1900")
    private Integer productionYear;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive number")
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = "Invalid number format for price")
    private BigDecimal price;

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