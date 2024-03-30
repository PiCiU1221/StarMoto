package com.piciu1221.starmoto.controller.carReference;

import com.piciu1221.starmoto.service.carReference.CarDoorCountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/car-references")
@RequiredArgsConstructor
public class CarDoorCountController {
    private final CarDoorCountService carReferenceService;

    @GetMapping("/door-counts")
    public ResponseEntity<?> getCarDoorCounts() {
        List<String> doorCounts = carReferenceService.getAllDoorCounts();

        return ResponseEntity.status(HttpStatus.OK)
                .body(doorCounts);
    }
}
