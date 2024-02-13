package com.piciu1221.starmoto.repository.carReference;

import com.piciu1221.starmoto.model.carReference.CarBodyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarBodyTypeRepository extends JpaRepository<CarBodyType, Long> {
    Optional<CarBodyType> findByBodyTypeName(String bodyTypeName);
}