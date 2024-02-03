package com.piciu1221.starmoto.repository.reference;

import com.piciu1221.starmoto.model.reference.CarSeatCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarSeatCountRepository extends JpaRepository<CarSeatCount, Integer> {
    Optional<CarSeatCount> findBySeatsCount(Integer seatsCount);
}