package com.piciu1221.starmoto.repository.reference;

import com.piciu1221.starmoto.model.reference.CarColor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarColorRepository extends JpaRepository<CarColor, Integer> {
    Optional<CarColor> findByColorName(String colorName);
}