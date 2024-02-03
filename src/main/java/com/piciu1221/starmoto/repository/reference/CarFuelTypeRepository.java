package com.piciu1221.starmoto.repository.reference;

import com.piciu1221.starmoto.model.reference.CarFuelType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarFuelTypeRepository extends JpaRepository<CarFuelType, Integer> {
    Optional<CarFuelType> findByFuelTypeName(String fuelTypeName);
}