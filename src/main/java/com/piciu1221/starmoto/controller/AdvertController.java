package com.piciu1221.starmoto.controller;

import com.piciu1221.starmoto.dto.AdvertPostRequestDTO;
import com.piciu1221.starmoto.dto.AdvertResponseDTO;
import com.piciu1221.starmoto.exception.AdvertAddException;
import com.piciu1221.starmoto.exception.AdvertNotFoundException;
import com.piciu1221.starmoto.exception.ApiErrorResponse;
import com.piciu1221.starmoto.service.AdvertService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/adverts")
public class AdvertController {

    private final AdvertService advertService;

    @Autowired
    public AdvertController(AdvertService advertService) {
        this.advertService = advertService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<?> addAdvert(@Valid AdvertPostRequestDTO advertPOSTRequestDTO) {
        try {
            AdvertResponseDTO savedAdvert = advertService.addAdvert(advertPOSTRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedAdvert);
        } catch (AdvertAddException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiErrorResponse("AdvertAddException", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiErrorResponse("InternalServerError", "Unexpected internal server error occurred: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSingleAdvertById(@PathVariable Long id) {
        try {
            AdvertResponseDTO advert = advertService.getAdvertById(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(advert);
        } catch (AdvertNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiErrorResponse("AdvertNotFoundException", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiErrorResponse("InternalServerError", "Unexpected internal server error occurred: " + e.getMessage()));
        }
    }
}
