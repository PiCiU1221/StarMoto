package com.piciu1221.starmoto.service;

import com.piciu1221.starmoto.dto.CarAddRequestDTO;
import com.piciu1221.starmoto.exception.AdvertAddException;
import com.piciu1221.starmoto.exception.AdvertUpdateException;
import com.piciu1221.starmoto.model.Car;
import com.piciu1221.starmoto.model.carReference.CarFeature;
import com.piciu1221.starmoto.model.carReference.CarImageCollection;
import com.piciu1221.starmoto.repository.CarRepository;
import com.piciu1221.starmoto.repository.carReference.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final CarMakeRepository carMakeRepository;
    private final CarModelRepository carModelRepository;
    private final CarBodyTypeRepository carBodyTypeRepository;
    private final CarColorRepository carColorRepository;
    private final CarFuelTypeRepository carFuelTypeRepository;
    private final CarTransmissionTypeRepository carTransmissionTypeRepository;
    private final CarDoorCountRepository carDoorCountRepository;
    private final CarSeatCountRepository carSeatCountRepository;
    private final CarDrivetrainTypeRepository carDrivetrainTypeRepository;
    private final CarFeatureRepository carFeatureRepository;
    private final CarImageCollectionRepository carImageCollectionRepository;

    @Transactional
    public Car addCar(@Valid CarAddRequestDTO carAddRequestDTO) {

        Car car = new Car();

        car.setVin(carAddRequestDTO.getVin());

        car.setMake(carMakeRepository.findByMakeName(carAddRequestDTO.getMake())
                .orElseThrow(() -> new AdvertAddException("Car make not found")));

        car.setModel(carModelRepository.findByModelNameAndMakeId(carAddRequestDTO.getModel(), car.getMake())
                .orElseThrow(() -> new AdvertAddException("Car model not found")));

        car.setBodyType(carBodyTypeRepository.findByBodyTypeName(carAddRequestDTO.getBodyType())
                .orElseThrow(() -> new AdvertAddException("Car body type not found")));

        car.setColor(carColorRepository.findByColorName(carAddRequestDTO.getColor())
                .orElseThrow(() -> new AdvertAddException("Car color not found")));

        car.setFuelType(carFuelTypeRepository.findByFuelTypeName(carAddRequestDTO.getFuelType())
                .orElseThrow(() -> new AdvertAddException("Car fuel type not found")));

        car.setTransmissionType(carTransmissionTypeRepository.findByTransmissionTypeName(carAddRequestDTO.getTransmissionType())
                .orElseThrow(() -> new AdvertAddException("Car transmission type not found")));

        car.setDrivetrainType(carDrivetrainTypeRepository.findByDrivetrainTypeName(carAddRequestDTO.getDrivetrainType())
                .orElseThrow(() -> new AdvertAddException("Car drivetrain type not found")));

        car.setDoors(carDoorCountRepository.findByDoorCount(carAddRequestDTO.getDoorsCount())
                .orElseThrow(() -> new AdvertAddException("Car door count not found")));

        car.setSeats(carSeatCountRepository.findBySeatCount(carAddRequestDTO.getSeatsCount())
                .orElseThrow(() -> new AdvertAddException("Car seat count not found")));

        car.setProductionYear(carAddRequestDTO.getProductionYear());
        car.setMileage(carAddRequestDTO.getMileage());
        car.setEnginePower(carAddRequestDTO.getEnginePower());
        car.setEngineCapacity(carAddRequestDTO.getEngineCapacity());
        car.setIsDamaged(carAddRequestDTO.getIsDamaged());

        // Car features
        List<String> features = carAddRequestDTO.getFeatures();
        List<CarFeature> carFeatures = new ArrayList<>();

        for (String feature : features) {
            CarFeature carFeature = carFeatureRepository.findByFeatureName(feature);

            if (carFeature == null) {
                throw new AdvertAddException("Feature: " + feature + " not in the database");
            }

            carFeatures.add(carFeature);
        }

        car.setFeatures(carFeatures);

        car = carRepository.save(car);

        CarImageCollection carImageCollection = new CarImageCollection();
        carImageCollection.setCar(car);

        car.setImageCollection(carImageCollection);

        carImageCollectionRepository.save(carImageCollection);

        return car;
    }

    @Transactional
    public Car updateCar(Long id, @Valid CarAddRequestDTO carAddRequestDTO) {
        Car car = carRepository.findById(id)
                // We should never get here, as the car has the same ID as the advert, but just in case
                .orElseThrow(() -> new AdvertUpdateException("Car with ID " + id + " not found"));

        car.setVin(carAddRequestDTO.getVin());

        car.setMake(carMakeRepository.findByMakeName(carAddRequestDTO.getMake())
                .orElseThrow(() -> new AdvertUpdateException("Car make not found")));

        car.setModel(carModelRepository.findByModelNameAndMakeId(carAddRequestDTO.getModel(), car.getMake())
                .orElseThrow(() -> new AdvertUpdateException("Car model not found")));

        car.setBodyType(carBodyTypeRepository.findByBodyTypeName(carAddRequestDTO.getBodyType())
                .orElseThrow(() -> new AdvertUpdateException("Car body type not found")));

        car.setColor(carColorRepository.findByColorName(carAddRequestDTO.getColor())
                .orElseThrow(() -> new AdvertUpdateException("Car color not found")));

        car.setFuelType(carFuelTypeRepository.findByFuelTypeName(carAddRequestDTO.getFuelType())
                .orElseThrow(() -> new AdvertUpdateException("Car fuel type not found")));

        car.setTransmissionType(carTransmissionTypeRepository.findByTransmissionTypeName(carAddRequestDTO.getTransmissionType())
                .orElseThrow(() -> new AdvertUpdateException("Car transmission type not found")));

        car.setDrivetrainType(carDrivetrainTypeRepository.findByDrivetrainTypeName(carAddRequestDTO.getDrivetrainType())
                .orElseThrow(() -> new AdvertUpdateException("Car drivetrain type not found")));

        car.setDoors(carDoorCountRepository.findByDoorCount(carAddRequestDTO.getDoorsCount())
                .orElseThrow(() -> new AdvertUpdateException("Car door count not found")));

        car.setSeats(carSeatCountRepository.findBySeatCount(carAddRequestDTO.getSeatsCount())
                .orElseThrow(() -> new AdvertUpdateException("Car seat count not found")));

        car.setProductionYear(carAddRequestDTO.getProductionYear());
        car.setMileage(carAddRequestDTO.getMileage());
        car.setEnginePower(carAddRequestDTO.getEnginePower());
        car.setEngineCapacity(carAddRequestDTO.getEngineCapacity());
        car.setIsDamaged(carAddRequestDTO.getIsDamaged());

        // Car features
        List<String> features = carAddRequestDTO.getFeatures();
        List<CarFeature> carFeatures = new ArrayList<>();

        for (String feature : features) {
            CarFeature carFeature = carFeatureRepository.findByFeatureName(feature);

            if (carFeature == null) {
                throw new AdvertUpdateException("Feature: " + feature + " not in the database");
            }

            carFeatures.add(carFeature);
        }

        car.setFeatures(carFeatures);

        return car;
    }
}
