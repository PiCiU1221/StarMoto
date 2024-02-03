package com.piciu1221.starmoto.repository.reference;

import com.piciu1221.starmoto.model.reference.CarDoorCount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarDoorCountRepository extends JpaRepository<CarDoorCount, Integer> {
    Optional<CarDoorCount> findByDoorCount(Integer doorsCount);
}