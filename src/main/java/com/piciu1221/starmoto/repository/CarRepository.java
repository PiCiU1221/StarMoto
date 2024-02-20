package com.piciu1221.starmoto.repository;

import com.piciu1221.starmoto.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}