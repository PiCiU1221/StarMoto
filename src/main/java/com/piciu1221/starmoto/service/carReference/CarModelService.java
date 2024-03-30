package com.piciu1221.starmoto.service.carReference;

import com.piciu1221.starmoto.exception.MakeNotFoundException;
import com.piciu1221.starmoto.model.carReference.CarMake;
import com.piciu1221.starmoto.model.carReference.CarModel;
import com.piciu1221.starmoto.repository.carReference.CarMakeRepository;
import com.piciu1221.starmoto.repository.carReference.CarModelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarModelService {
    private final CarMakeRepository carMakeRepository;
    private final CarModelRepository carModelRepository;

    public List<String> getAllModels(String make) {
        CarMake carMake = carMakeRepository.findByMakeName(make)
                .orElseThrow(() -> new MakeNotFoundException("Make not found"));

        List<CarModel> models = carModelRepository.findByMake(carMake);

        return models.stream()
                .map(CarModel::getModelName)
                .toList();
    }
}
