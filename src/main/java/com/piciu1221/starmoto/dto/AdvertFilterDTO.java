package com.piciu1221.starmoto.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdvertFilterDTO {
    private String make;
    private String model;
    private String bodyType;
    private String color;
    private String fuelType;
    private String transmissionType;
    private String drivetrainType;
    private int doors;
    private int seats;
    private int minYear;
    private int maxYear;
    private int minPrice;
    private int maxPrice;
    private int minMileage;
    private int maxMileage;
    private int minEnginePower;
    private int maxEnginePower;
    private int minEngineCapacity;
    private int maxEngineCapacity;
    private Boolean isDamaged;
    private List<String> features;
}