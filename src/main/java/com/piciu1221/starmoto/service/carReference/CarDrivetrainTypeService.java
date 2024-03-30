package com.piciu1221.starmoto.service.carReference;

import com.piciu1221.starmoto.model.carReference.CarDrivetrainType;
import com.piciu1221.starmoto.repository.carReference.CarDrivetrainTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarDrivetrainTypeService {
    private final CarDrivetrainTypeRepository carDrivetrainTypeRepository;

    public List<String> getAllDrivetrainTypes() {
        List<CarDrivetrainType> drivetrainTypes = carDrivetrainTypeRepository.findAll();

        return drivetrainTypes.stream()
                .map(CarDrivetrainType::getDrivetrainTypeName)
                .toList();
    }
}
