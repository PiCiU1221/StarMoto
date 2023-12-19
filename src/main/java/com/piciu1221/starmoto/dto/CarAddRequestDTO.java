package com.piciu1221.starmoto.dto;

import com.piciu1221.starmoto.model.Car;
import com.piciu1221.starmoto.model.User;
import com.piciu1221.starmoto.model.reference.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CarAddRequestDTO {
    private Long sellerId;
    private Integer modelId;
    private Integer typeId;
    private Integer conditionId;
    private Integer colorId;
    private Integer fuelTypeId;
    private Integer transmissionId;
    private Integer doorsId;
    private Integer seatsId;
    private Integer drivetrainId;
    private Integer productionYear;
    private BigDecimal price;
    private Integer mileage;
    private Integer horsepower;
    private Integer engineCapacity;
    private String description;
    private List<Integer> featureIds;

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