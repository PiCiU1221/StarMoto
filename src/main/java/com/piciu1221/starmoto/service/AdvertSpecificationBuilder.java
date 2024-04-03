package com.piciu1221.starmoto.service;

import com.piciu1221.starmoto.dto.AdvertFilterDTO;
import com.piciu1221.starmoto.model.Advert;
import com.piciu1221.starmoto.model.Car;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class AdvertSpecificationBuilder {
    public static Specification<Advert> buildSpecification(AdvertFilterDTO advertFilterDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            Join<Advert, Car> carJoin = root.join("car");

            if (advertFilterDTO.getMake() != null) {
                predicates.add(criteriaBuilder.equal(carJoin.get("make").get("makeName"), advertFilterDTO.getMake()));
            }

            if (advertFilterDTO.getModel() != null) {
                predicates.add(criteriaBuilder.equal(carJoin.get("model").get("modelName"), advertFilterDTO.getModel()));
            }

            if (advertFilterDTO.getBodyType() != null) {
                predicates.add(criteriaBuilder.equal(carJoin.get("bodyType").get("bodyTypeName"), advertFilterDTO.getBodyType()));
            }

            if (advertFilterDTO.getColor() != null) {
                predicates.add(criteriaBuilder.equal(carJoin.get("color").get("colorName"), advertFilterDTO.getColor()));
            }

            if (advertFilterDTO.getFuelType() != null) {
                predicates.add(criteriaBuilder.equal(carJoin.get("fuelType").get("fuelTypeName"), advertFilterDTO.getFuelType()));
            }

            if (advertFilterDTO.getTransmissionType() != null) {
                predicates.add(criteriaBuilder.equal(carJoin.get("transmissionType").get("transmissionTypeName"), advertFilterDTO.getTransmissionType()));
            }

            if (advertFilterDTO.getDrivetrainType() != null) {
                predicates.add(criteriaBuilder.equal(carJoin.get("drivetrainType").get("drivetrainTypeName"), advertFilterDTO.getDrivetrainType()));
            }

            if (advertFilterDTO.getDoors() > 0) {
                predicates.add(criteriaBuilder.equal(carJoin.get("doors").get("doorCount"), advertFilterDTO.getDoors()));
            }

            if (advertFilterDTO.getSeats() > 0) {
                predicates.add(criteriaBuilder.equal(carJoin.get("seats").get("seatCount"), advertFilterDTO.getSeats()));
            }

            if (advertFilterDTO.getMinYear() > 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(carJoin.get("productionYear"), advertFilterDTO.getMinYear()));
            }

            if (advertFilterDTO.getMaxYear() > 0) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(carJoin.get("productionYear"), advertFilterDTO.getMaxYear()));
            }

            if (advertFilterDTO.getMinMileage() > 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(carJoin.get("mileage"), advertFilterDTO.getMinMileage()));
            }

            if (advertFilterDTO.getMaxMileage() > 0) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(carJoin.get("mileage"), advertFilterDTO.getMaxMileage()));
            }

            if (advertFilterDTO.getMinEnginePower() > 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(carJoin.get("enginePower"), advertFilterDTO.getMinEnginePower()));
            }

            if (advertFilterDTO.getMaxEnginePower() > 0) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(carJoin.get("enginePower"), advertFilterDTO.getMaxEnginePower()));
            }

            if (advertFilterDTO.getMinEngineCapacity() > 0) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(carJoin.get("engineCapacity"), advertFilterDTO.getMinEngineCapacity()));
            }

            if (advertFilterDTO.getMaxEngineCapacity() > 0) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(carJoin.get("engineCapacity"), advertFilterDTO.getMaxEngineCapacity()));
            }

            if (advertFilterDTO.getIsDamaged() != null) {
                predicates.add(criteriaBuilder.equal(carJoin.get("isDamaged"), advertFilterDTO.getIsDamaged()));
            }

            if (advertFilterDTO.getFeatures() != null && !advertFilterDTO.getFeatures().isEmpty()) {
                predicates.add(carJoin.get("features").get("featureName").in(advertFilterDTO.getFeatures()));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
