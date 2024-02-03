package com.piciu1221.starmoto.repository;

import com.piciu1221.starmoto.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Integer> {

    Optional<Object> findByVin(String vin);

    boolean existsByVin(String vin);
}