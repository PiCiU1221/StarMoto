package com.piciu1221.starmoto.repository.carReference;

import com.piciu1221.starmoto.model.carReference.CarMake;
import com.piciu1221.starmoto.model.carReference.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarModelRepository extends JpaRepository<CarModel, CarMake> {
    Optional<CarModel> findByModelNameAndMakeId(String modelName, CarMake makeId);
}