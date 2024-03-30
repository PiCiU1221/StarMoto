package com.piciu1221.starmoto.service.carReference;

import com.piciu1221.starmoto.model.carReference.CarFuelType;
import com.piciu1221.starmoto.repository.carReference.CarFuelTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarFuelTypeService {
    CarFuelTypeRepository carFuelTypeRepository;

    public List<String> getAllFuelTypes() {
        List<CarFuelType> fuelTypes = carFuelTypeRepository.findAll();

        return fuelTypes.stream()
                .map(CarFuelType::getFuelTypeName)
                .toList();
    }
}
