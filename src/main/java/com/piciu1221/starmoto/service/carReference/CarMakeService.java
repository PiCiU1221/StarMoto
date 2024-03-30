package com.piciu1221.starmoto.service.carReference;

import com.piciu1221.starmoto.model.carReference.CarMake;
import com.piciu1221.starmoto.repository.carReference.CarMakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarMakeService {
    CarMakeRepository carMakeRepository;

    public List<String> getAllMakes() {
        List<CarMake> makes = carMakeRepository.findAll();

        return makes.stream()
                .map(CarMake::getMakeName)
                .toList();
    }
}
