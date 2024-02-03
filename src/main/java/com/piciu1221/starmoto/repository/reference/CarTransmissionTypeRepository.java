package com.piciu1221.starmoto.repository.reference;

import com.piciu1221.starmoto.model.reference.CarTransmissionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarTransmissionTypeRepository extends JpaRepository<CarTransmissionType, Integer> {
    Optional<CarTransmissionType> findByTransmissionTypeName(String transmissionTypeName);
}