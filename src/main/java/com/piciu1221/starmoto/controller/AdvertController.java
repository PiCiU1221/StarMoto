package com.piciu1221.starmoto.controller;

import com.piciu1221.starmoto.dto.AdvertFilterDTO;
import com.piciu1221.starmoto.dto.AdvertPostRequestDTO;
import com.piciu1221.starmoto.dto.AdvertResponseDTO;
import com.piciu1221.starmoto.exception.AdvertAddException;
import com.piciu1221.starmoto.exception.AdvertNotFoundException;
import com.piciu1221.starmoto.exception.AdvertUpdateException;
import com.piciu1221.starmoto.exception.ApiErrorResponse;
import com.piciu1221.starmoto.service.AdvertService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/adverts")
public class AdvertController {

    private final AdvertService advertService;

    @Autowired
    public AdvertController(AdvertService advertService) {
        this.advertService = advertService;
    }

    @PostMapping
    public ResponseEntity<?> addAdvert(@Valid @RequestBody AdvertPostRequestDTO advertPOSTRequestDTO,
                                       Authentication authentication) {
        String username = authentication.getName();

        try {
            AdvertResponseDTO savedAdvert = advertService.addAdvert(advertPOSTRequestDTO, username);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedAdvert);
        } catch (AdvertAddException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiErrorResponse("AdvertAddException", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllAdverts(@ModelAttribute AdvertFilterDTO advertFilterDTO,
                                           @RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        List<AdvertResponseDTO> adverts = advertService.getAllAdverts(advertFilterDTO, page, size);

        return ResponseEntity.status(HttpStatus.OK)
                .body(adverts);
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
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> updateAdvert(@PathVariable Long id,
                                          @Valid @RequestBody AdvertPostRequestDTO advertPOSTRequestDTO,
                                          Authentication authentication) {
        String username = authentication.getName();

        try {
            AdvertResponseDTO updatedAdvert = advertService.updateAdvert(id, advertPOSTRequestDTO, username);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(updatedAdvert);
        } catch (AdvertNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiErrorResponse("AdvertNotFoundException", e.getMessage()));
        } catch (AdvertUpdateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiErrorResponse("AdvertUpdateException", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAdvert(@PathVariable Long id,
                                          Authentication authentication) {
        String username = authentication.getName();

        try {
            advertService.deleteAdvert(id, username);
            return ResponseEntity.status(HttpStatus.OK)
                    .build();
        } catch (AdvertNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiErrorResponse("AdvertNotFoundException", e.getMessage()));
        }
    }
}
