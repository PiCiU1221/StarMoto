package com.piciu1221.starmoto.repository.carReference;

import com.piciu1221.starmoto.model.carReference.CarDrivetrainType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarDrivetrainTypeRepository extends JpaRepository<CarDrivetrainType, Long> {
    Optional<CarDrivetrainType> findByDrivetrainTypeName(String drivetrainTypeName);
}