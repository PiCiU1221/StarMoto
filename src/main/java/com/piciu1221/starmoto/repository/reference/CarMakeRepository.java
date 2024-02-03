package com.piciu1221.starmoto.repository.reference;

import com.piciu1221.starmoto.model.reference.CarMake;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarMakeRepository extends JpaRepository<CarMake, Integer> {
    Optional<CarMake> findByMakeName(String makeName);
}