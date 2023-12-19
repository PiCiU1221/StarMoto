package com.piciu1221.starmoto.repository.reference;

import com.piciu1221.starmoto.model.reference.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarModelRepository extends JpaRepository<CarModel, Integer> {
}