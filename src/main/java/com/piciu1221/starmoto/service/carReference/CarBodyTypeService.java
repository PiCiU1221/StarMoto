package com.piciu1221.starmoto.service.carReference;

import com.piciu1221.starmoto.model.carReference.CarBodyType;
import com.piciu1221.starmoto.repository.carReference.CarBodyTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarBodyTypeService {
    private final CarBodyTypeRepository carBodyTypeRepository;

    public List<String> getAllBodyTypes() {
        List<CarBodyType> bodyTypes = carBodyTypeRepository.findAll();

        return bodyTypes.stream()
                .map(CarBodyType::getBodyTypeName)
                .toList();
    }
}
