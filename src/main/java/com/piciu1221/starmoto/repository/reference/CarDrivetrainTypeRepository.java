package com.piciu1221.starmoto.repository.reference;

import com.piciu1221.starmoto.model.reference.CarDrivetrainType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarDrivetrainTypeRepository extends JpaRepository<CarDrivetrainType, Integer> {
    Optional<CarDrivetrainType> findByDrivetrainTypeName(String drivetrainTypeName);
}