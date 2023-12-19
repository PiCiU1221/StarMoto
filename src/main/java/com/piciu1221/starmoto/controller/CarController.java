package com.piciu1221.starmoto.controller;

import com.piciu1221.starmoto.dto.CarAddRequestDTO;
import com.piciu1221.starmoto.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    @Autowired
    private CarService carService;

    @PostMapping("/add")
    public ResponseEntity<String> addCar(@RequestBody CarAddRequestDTO carAddRequestDTO) {
        //carService.addCar(carAddRequestDTO);
        return ResponseEntity.ok("Car added successfully");
    }
}
