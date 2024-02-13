package com.piciu1221.starmoto.repository.carReference;

import com.piciu1221.starmoto.model.carReference.CarColor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarColorRepository extends JpaRepository<CarColor, Long> {
    Optional<CarColor> findByColorName(String colorName);
}