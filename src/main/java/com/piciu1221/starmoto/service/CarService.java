package com.piciu1221.starmoto.service;

import com.piciu1221.starmoto.dto.CarAddRequestDTO;
import com.piciu1221.starmoto.model.Car;
import com.piciu1221.starmoto.model.User;
import com.piciu1221.starmoto.model.reference.*;
import com.piciu1221.starmoto.repository.CarRepository;
import com.piciu1221.starmoto.repository.UserRepository;
import com.piciu1221.starmoto.repository.reference.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final CarModelRepository carModelRepository;
    private final CarTypeRepository carTypeRepository;
    private final CarConditionRepository carConditionRepository;
    private final CarColorRepository carColorRepository;
    private final CarFuelTypeRepository carFuelTypeRepository;
    private final CarTransmissionTypeRepository carTransmissionTypeRepository;
    private final CarDoorCountRepository carDoorCountRepository;
    private final CarSeatCountRepository carSeatCountRepository;
    private final CarDrivetrainTypeRepository carDrivetrainTypeRepository;
    private final CarFeatureRepository carFeatureRepository;

    public Car addCar(CarAddRequestDTO carAddRequestDTO) throws IOException {
        User seller = userRepository.findById(carAddRequestDTO.getSellerId())
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        CarModel model = carModelRepository.findById(carAddRequestDTO.getModelId())
                .orElseThrow(() -> new RuntimeException("Car model not found"));

        CarType type = carTypeRepository.findById(carAddRequestDTO.getTypeId())
                .orElseThrow(() -> new RuntimeException("Car type not found"));

        CarCondition condition = carConditionRepository.findById(carAddRequestDTO.getConditionId())
                .orElseThrow(() -> new RuntimeException("Car condition not found"));

        CarColor color = carColorRepository.findById(carAddRequestDTO.getColorId())
                .orElseThrow(() -> new RuntimeException("Car color not found"));

        CarFuelType fuelType = carFuelTypeRepository.findById(carAddRequestDTO.getFuelTypeId())
                .orElseThrow(() -> new RuntimeException("Car fuel type not found"));

        CarTransmissionType transmissionType = carTransmissionTypeRepository.findById(carAddRequestDTO.getTransmissionId())
                .orElseThrow(() -> new RuntimeException("Car transmission type not found"));

        CarDoorCount doors = carDoorCountRepository.findById(carAddRequestDTO.getDoorsId())
                .orElseThrow(() -> new RuntimeException("Car doors count not found"));

        CarSeatCount seats = carSeatCountRepository.findById(carAddRequestDTO.getSeatsId())
                .orElseThrow(() -> new RuntimeException("Car seats count not found"));

        CarDrivetrainType drivetrainType = carDrivetrainTypeRepository.findById(carAddRequestDTO.getDrivetrainId())
                .orElseThrow(() -> new RuntimeException("Car drivetrain type not found"));

        // Set features if provided
        List<CarFeature> features = carFeatureRepository.findAllById(carAddRequestDTO.getFeatureIds());

        // Create the car entity
        Car car = carAddRequestDTO.toEntity(seller, model, type, condition, color, fuelType,
                transmissionType, doors, seats, drivetrainType, features);

        // Save the car entity to the database
        return carRepository.save(car);
    }
}
