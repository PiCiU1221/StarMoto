package com.piciu1221.starmoto.repository.reference;

import com.piciu1221.starmoto.model.reference.CarBodyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarTypeRepository extends JpaRepository<CarBodyType, Integer> {
    Optional<CarBodyType> findByBodyTypeName(String bodyTypeName);
}