package com.piciu1221.starmoto.repository.carReference;

import com.piciu1221.starmoto.model.carReference.CarSeatCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarSeatCountRepository extends JpaRepository<CarSeatCount, Long> {
    Optional<CarSeatCount> findBySeatCount(Integer seatsCount);
}