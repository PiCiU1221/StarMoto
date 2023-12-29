package com.piciu1221.starmoto.dto;

import com.piciu1221.starmoto.model.Car;
import com.piciu1221.starmoto.model.User;
import com.piciu1221.starmoto.model.reference.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CarAddRequestDTO {
    @NotNull(message = "Seller ID is required")
    @Positive(message = "Seller ID must be a positive integer")
    private Long sellerId;

    @NotNull(message = "Model ID is required")
    @Positive(message = "Model ID must be a positive integer")
    private Integer modelId;

    @NotNull(message = "Type ID is required")
    @Positive(message = "Type ID must be a positive integer")
    private Integer typeId;

    @NotNull(message = "Condition ID is required")
    @Positive(message = "Condition ID must be a positive integer")
    private Integer conditionId;

    @NotNull(message = "Color ID is required")
    @Positive(message = "Color ID must be a positive integer")
    private Integer colorId;

    @NotNull(message = "Fuel Type ID is required")
    @Positive(message = "Fuel Type ID must be a positive integer")
    private Integer fuelTypeId;

    @NotNull(message = "Transmission ID is required")
    @Positive(message = "Transmission ID must be a positive integer")
    private Integer transmissionId;

    @NotNull(message = "Doors ID is required")
    @Positive(message = "Doors ID must be a positive integer")
    private Integer doorsId;

    @NotNull(message = "Seats ID is required")
    @Positive(message = "Seats ID must be a positive integer")
    private Integer seatsId;

    @NotNull(message = "Drivetrain ID is required")
    @Positive(message = "Drivetrain ID must be a positive integer")
    private Integer drivetrainId;

    @NotNull(message = "Production Year is required")
    @Min(value = 1886, message = "Production year should be at least 1886")
    private Integer productionYear;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be a positive number")
    @Digits(integer = Integer.MAX_VALUE, fraction = 2, message = "Invalid number format for price")
    private BigDecimal price;

    @NotNull(message = "Mileage is required")
    @Min(value = 0, message = "Mileage cannot be negative")
    private Integer mileage;

    @NotNull(message = "Horsepower is required")
    @Min(value = 1, message = "Horsepower should be at least 1")
    private Integer horsepower;

    @NotNull(message = "Engine Capacity is required")
    @Min(value = 1, message = "Engine capacity should be at least 1")
    private Integer engineCapacity;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @Size(max = 7, message = "Up to 7 features are allowed")
    private List<@Positive(message = "Feature ID must be a positive integer") Integer> featureIds;

    private List<MultipartFile> images;

    public Car toEntity(User seller, CarModel model, CarType type, CarCondition condition,
                        CarColor color, CarFuelType fuelType, CarTransmissionType transmissionType,
                        CarDoorCount doors, CarSeatCount seats, CarDrivetrainType drivetrainType,
                        List<CarFeature> features) {
        Car car = new Car();
        car.setSeller(seller);
        car.setModel(model);
        car.setType(type);
        car.setCondition(condition);
        car.setColor(color);
        car.setFuelType(fuelType);
        car.setTransmissionType(transmissionType);
        car.setDoors(doors);
        car.setSeats(seats);
        car.setDrivetrainType(drivetrainType);
        car.setProductionYear(this.getProductionYear());
        car.setPrice(this.getPrice());
        car.setMileage(this.getMileage());
        car.setHorsepower(this.getHorsepower());
        car.setEngineCapacity(this.getEngineCapacity());
        car.setDescription(this.getDescription());

        car.setFeatures(features);

        return car;
    }
}