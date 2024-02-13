package com.piciu1221.starmoto.repository.carReference;

import com.piciu1221.starmoto.model.carReference.CarTransmissionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarTransmissionTypeRepository extends JpaRepository<CarTransmissionType, Long> {
    Optional<CarTransmissionType> findByTransmissionTypeName(String transmissionTypeName);
}