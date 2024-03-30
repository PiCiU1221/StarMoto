package com.piciu1221.starmoto.service.carReference;

import com.piciu1221.starmoto.model.carReference.CarDoorCount;
import com.piciu1221.starmoto.repository.carReference.CarDoorCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarDoorCountService {
    private final CarDoorCountRepository carDoorCountRepository;

    public List<String> getAllDoorCounts() {
        List<CarDoorCount> doorCounts = carDoorCountRepository.findAll();

        return doorCounts.stream()
                .map(CarDoorCount::getDoorCount)
                .map(String::valueOf)
                .toList();
    }
}
