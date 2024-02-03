package com.piciu1221.starmoto.controller;

import com.piciu1221.starmoto.dto.CarAddRequestDTO;
import com.piciu1221.starmoto.dto.CarAddResponseDTO;
import com.piciu1221.starmoto.exception.ApiErrorResponse;
import com.piciu1221.starmoto.exception.CarAddException;
import com.piciu1221.starmoto.service.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cars")
public class CarController {
    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping(value = "/add", consumes = "multipart/form-data")
    public ResponseEntity<?> addCar(@Valid CarAddRequestDTO carAddRequestDTO) {
        try {
            CarAddResponseDTO savedCar = carService.addCar(carAddRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedCar);
        } catch (CarAddException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiErrorResponse("CarAddException", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiErrorResponse("InternalServerError", "Unexpected internal server error occurred: " + e.getMessage()));
        }
    }
}
