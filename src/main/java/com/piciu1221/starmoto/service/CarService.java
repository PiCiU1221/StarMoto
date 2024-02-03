package com.piciu1221.starmoto.service;

import com.piciu1221.starmoto.dto.CarAddRequestDTO;
import com.piciu1221.starmoto.dto.CarAddResponseDTO;
import com.piciu1221.starmoto.exception.CarAddException;
import com.piciu1221.starmoto.model.Car;
import com.piciu1221.starmoto.model.reference.CarFeature;
import com.piciu1221.starmoto.model.reference.CarImageUrl;
import com.piciu1221.starmoto.repository.CarRepository;
import com.piciu1221.starmoto.repository.UserRepository;
import com.piciu1221.starmoto.repository.reference.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarService {

    private final CarRepository carRepository;
    private final UserRepository userRepository;
    private final CarMakeRepository carMakeRepository;
    private final CarModelRepository carModelRepository;
    private final CarTypeRepository carTypeRepository;
    private final CarColorRepository carColorRepository;
    private final CarFuelTypeRepository carFuelTypeRepository;
    private final CarTransmissionTypeRepository carTransmissionTypeRepository;
    private final CarDoorCountRepository carDoorCountRepository;
    private final CarSeatCountRepository carSeatCountRepository;
    private final CarDrivetrainTypeRepository carDrivetrainTypeRepository;
    private final CarFeatureRepository carFeatureRepository;
    private final CarImageUrlRepository carImageUrlRepository;

    private final CarImageService carImageService;

    @Transactional
    public CarAddResponseDTO addCar(@Valid CarAddRequestDTO carAddRequestDTO) throws IOException {

        Car car = new Car();

        if (carRepository.existsByVin(carAddRequestDTO.getVin())) {
            throw new CarAddException("Vin is already in use");
        }

        car.setVin(carAddRequestDTO.getVin());

        car.setSeller(userRepository.findById(carAddRequestDTO.getSellerId())
                .orElseThrow(() -> new CarAddException("Seller not found")));

        car.setMake(carMakeRepository.findByMakeName(carAddRequestDTO.getMakeName())
                .orElseThrow(() -> new CarAddException("Car make not found")));

        car.setModel(carModelRepository.findByModelNameAndMakeId(carAddRequestDTO.getModelName(), car.getMake())
                .orElseThrow(() -> new CarAddException("Car model not found")));

        car.setBodyType(carTypeRepository.findByBodyTypeName(carAddRequestDTO.getBodyTypeName())
                .orElseThrow(() -> new CarAddException("Car body type not found")));

        car.setColor(carColorRepository.findByColorName(carAddRequestDTO.getColorName())
                .orElseThrow(() -> new CarAddException("Car color not found")));

        car.setFuelType(carFuelTypeRepository.findByFuelTypeName(carAddRequestDTO.getFuelTypeName())
                .orElseThrow(() -> new CarAddException("Car fuel type not found")));

        car.setTransmissionType(carTransmissionTypeRepository.findByTransmissionTypeName(carAddRequestDTO.getTransmissionTypeName())
                .orElseThrow(() -> new CarAddException("Car transmission type not found")));

        car.setDrivetrainType(carDrivetrainTypeRepository.findByDrivetrainTypeName(carAddRequestDTO.getDrivetrainTypeName())
                .orElseThrow(() -> new CarAddException("Car drivetrain type not found")));

        car.setDoors(carDoorCountRepository.findByDoorCount(carAddRequestDTO.getDoorsCount())
                .orElseThrow(() -> new CarAddException("Car door count not found")));

        car.setSeats(carSeatCountRepository.findBySeatsCount(carAddRequestDTO.getSeatsCount())
                .orElseThrow(() -> new CarAddException("Car seat count not found")));

        car.setProductionYear(carAddRequestDTO.getProductionYear());
        car.setPrice(carAddRequestDTO.getPrice());
        car.setMileage(carAddRequestDTO.getMileage());
        car.setEnginePower(carAddRequestDTO.getEnginePower());
        car.setEngineCapacity(carAddRequestDTO.getEngineCapacity());
        car.setIsDamaged(carAddRequestDTO.getIsDamaged());

        // Car features

        List<String> features = carAddRequestDTO.getFeatures();
        List<CarFeature> carFeatures = new ArrayList<>();

        for (String feature : features) {
            CarFeature carFeature = carFeatureRepository.findByFeatureName(feature);
            carFeatures.add(carFeature);
        }

        car.setFeatures(carFeatures);

        // Car images

        List<MultipartFile> images = carAddRequestDTO.getImages();
        List<CarImageUrl> carImageUrls = new ArrayList<>();

        for (MultipartFile image : images) {
            String imageUrl = null;

            try {
                imageUrl = carImageService.uploadImage(image);
            } catch (CarAddException e) {
                throw new CarAddException(e.getMessage());
            }

            if (carImageUrlRepository.existsByImageUrl(imageUrl)) {
                throw new CarAddException("One image has already been uploaded. Duplicate detected");
            }

            if (imageUrl != null) {
                CarImageUrl carImageUrl = new CarImageUrl();
                carImageUrl.setImageUrl(imageUrl);

                CarImageUrl savedCarImageUrl = carImageUrlRepository.save(carImageUrl);
                carImageUrls.add(savedCarImageUrl);
            } else {
                throw new CarAddException("Failed to upload one or more images");
            }
        }

        car.setImageUrls(carImageUrls);

        return new CarAddResponseDTO(carRepository.save(car));
    }
}
