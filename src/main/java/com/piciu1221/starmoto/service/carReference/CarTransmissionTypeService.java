package com.piciu1221.starmoto.service.carReference;

import com.piciu1221.starmoto.model.carReference.CarTransmissionType;
import com.piciu1221.starmoto.repository.carReference.CarTransmissionTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarTransmissionTypeService {
    private final CarTransmissionTypeRepository carTransmissionTypeRepository;

    public List<String> getAllTransmissionTypes() {
        List<CarTransmissionType> transmissionTypes = carTransmissionTypeRepository.findAll();

        return transmissionTypes.stream()
                .map(CarTransmissionType::getTransmissionTypeName)
                .toList();
    }
}
