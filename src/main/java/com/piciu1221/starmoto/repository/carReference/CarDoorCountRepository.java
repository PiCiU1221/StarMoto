package com.piciu1221.starmoto.repository.carReference;

import com.piciu1221.starmoto.model.carReference.CarDoorCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarDoorCountRepository extends JpaRepository<CarDoorCount, Long> {
    Optional<CarDoorCount> findByDoorCount(Integer doorsCount);
}