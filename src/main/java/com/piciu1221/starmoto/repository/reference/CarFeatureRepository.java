package com.piciu1221.starmoto.repository.reference;

import com.piciu1221.starmoto.model.reference.CarFeature;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarFeatureRepository extends JpaRepository<CarFeature, Integer> {
    CarFeature findByFeatureName(String s);
}