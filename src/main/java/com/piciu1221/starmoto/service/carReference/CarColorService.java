package com.piciu1221.starmoto.service.carReference;

import com.piciu1221.starmoto.model.carReference.CarColor;
import com.piciu1221.starmoto.repository.carReference.CarColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarColorService {
    private final CarColorRepository carColorRepository;

    public List<String> getAllColors() {
        List<CarColor> brands = carColorRepository.findAll();

        return brands.stream()
                .map(CarColor::getColorName)
                .toList();
    }
}
