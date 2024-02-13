package com.piciu1221.starmoto.repository.carReference;

import com.piciu1221.starmoto.model.carReference.CarFeature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarFeatureRepository extends JpaRepository<CarFeature, Long> {
    CarFeature findByFeatureName(String s);
}