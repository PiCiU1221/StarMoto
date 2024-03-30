package com.piciu1221.starmoto.service.carReference;

import com.piciu1221.starmoto.model.carReference.CarSeatCount;
import com.piciu1221.starmoto.repository.carReference.CarSeatCountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarSeatCountService {
    private final CarSeatCountRepository carSeatCountRepository;

    public List<String> getAllSeatCounts() {
        List<CarSeatCount> seatCounts = carSeatCountRepository.findAll();

        return seatCounts.stream()
                .map(CarSeatCount::getSeatCount)
                .map(String::valueOf)
                .toList();
    }
}
