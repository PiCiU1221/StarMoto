package com.piciu1221.starmoto.repository.carReference;

import com.piciu1221.starmoto.model.carReference.CarFuelType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarFuelTypeRepository extends JpaRepository<CarFuelType, Long> {
    Optional<CarFuelType> findByFuelTypeName(String fuelTypeName);
}