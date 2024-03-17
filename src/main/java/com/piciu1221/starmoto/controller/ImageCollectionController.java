package com.piciu1221.starmoto.controller;

import com.piciu1221.starmoto.dto.ImageCollectionDeleteRequestDTO;
import com.piciu1221.starmoto.dto.ImageCollectionPostRequest;
import com.piciu1221.starmoto.exception.ApiErrorResponse;
import com.piciu1221.starmoto.service.ImageCollectionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/imageCollections")
public class ImageCollectionController {

    private final ImageCollectionService imageCollectionService;

    @Autowired
    public ImageCollectionController(ImageCollectionService imageCollectionService) {
        this.imageCollectionService = imageCollectionService;
    }

    @PostMapping(value = "{collectionId}/images", consumes = "multipart/form-data")
    public ResponseEntity<?> addImagesToCollection(
            @PathVariable("collectionId") Long collectionId,
            @Valid ImageCollectionPostRequest imageCollectionPostRequest) {
        try {
            List<String> savedImages = imageCollectionService.addImagesToCollection(collectionId, imageCollectionPostRequest);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(savedImages);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiErrorResponse("InternalServerError", "Unexpected internal server error occurred: " + e.getMessage()));
        }
    }

    @GetMapping(value = "{collectionId}/images")
    public ResponseEntity<?> getImagesFromCollection(
            @PathVariable("collectionId") Long collectionId) {
        try {
            List<String> images = imageCollectionService.getImagesFromCollection(collectionId);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(images);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiErrorResponse("InternalServerError", e.getMessage()));
        }
    }

    @DeleteMapping(value = "{collectionId}/images")
    public ResponseEntity<?> deleteImagesFromCollection(
            @PathVariable("collectionId") Long collectionId,
            @Valid ImageCollectionDeleteRequestDTO imageCollectionDeleteRequestDTO) {
        try {
            imageCollectionService.deleteImagesFromCollection(collectionId, imageCollectionDeleteRequestDTO);
            return ResponseEntity.status(HttpStatus.OK)
                    .build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiErrorResponse("InternalServerError", e.getMessage()));
        }
    }
}
