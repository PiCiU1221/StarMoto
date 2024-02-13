package com.piciu1221.starmoto.repository.carReference;

import com.piciu1221.starmoto.model.carReference.CarMake;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarMakeRepository extends JpaRepository<CarMake, Long> {
    Optional<CarMake> findByMakeName(String makeName);
}