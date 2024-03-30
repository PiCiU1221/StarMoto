package com.piciu1221.starmoto.service.carReference;

import com.piciu1221.starmoto.model.carReference.CarFeature;
import com.piciu1221.starmoto.repository.carReference.CarFeatureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarFeatureService {
    private final CarFeatureRepository carFeatureRepository;

    public List<String> getAllFeatures() {
        List<CarFeature> features = carFeatureRepository.findAll();

        return features.stream()
                .map(CarFeature::getFeatureName)
                .toList();
    }
}
