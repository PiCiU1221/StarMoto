package com.piciu1221.starmoto.service;

import com.piciu1221.starmoto.dto.ImageCollectionDeleteRequestDTO;
import com.piciu1221.starmoto.dto.ImageCollectionPostRequest;
import com.piciu1221.starmoto.exception.AdvertAddException;
import com.piciu1221.starmoto.model.User;
import com.piciu1221.starmoto.model.carReference.CarImageCollection;
import com.piciu1221.starmoto.model.carReference.CarImageUrl;
import com.piciu1221.starmoto.repository.carReference.CarImageCollectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageCollectionService {

    private final CarImageCollectionRepository carImageCollectionRepository;

    private final CarImageService carImageService;

    @Transactional
    public List<String> addImagesToCollection(Long collectionId,
                                              ImageCollectionPostRequest imageCollectionPostRequest,
                                              String username) throws IOException {

        CarImageCollection carImageCollection = carImageCollectionRepository.findById(collectionId)
                .orElseThrow(() -> new IOException("Car image collection with ID " + collectionId + " not found"));

        User owner = carImageCollection.getCar().getAdvert().getSeller();
        String ownerUsername = owner.getUsername();

        if (!Objects.equals(ownerUsername, username)) {
            throw new AdvertAddException("You are not the owner of this advert. Cannot upload images.");
        }

        List<CarImageUrl> carImageUrls = new ArrayList<>();

        for (MultipartFile image : imageCollectionPostRequest.getImages()) {
            String savedImageUrl = carImageService.uploadImage(image);

            CarImageUrl carImageUrl = new CarImageUrl();
            carImageUrl.setImageUrl(savedImageUrl);
            carImageUrl.setCollection(carImageCollection);

            carImageUrls.add(carImageUrl);
        }

        carImageCollection.getImages().addAll(carImageUrls);
        carImageCollection.setUpdatedAt(LocalDateTime.now());

        List<String> imageUrls = new ArrayList<>();

        for (CarImageUrl imageUrl : carImageUrls) {
            imageUrls.add(imageUrl.getImageUrl());
        }

        return imageUrls;
    }

    @Transactional
    public List<String> getImagesFromCollection(Long collectionId) throws IOException {

        CarImageCollection carImageCollection = carImageCollectionRepository.findById(collectionId)
                .orElseThrow(() -> new IOException("Car image collection with ID " + collectionId + " not found"));

        List<CarImageUrl> carImageUrls = carImageCollection.getImages();

        List<String> imageUrls = new ArrayList<>();

        for (CarImageUrl imageUrl : carImageUrls) {
            imageUrls.add(imageUrl.getImageUrl());
        }

        return imageUrls;
    }

    @Transactional
    public void deleteImagesFromCollection(Long collectionId,
                                           ImageCollectionDeleteRequestDTO imageCollectionDeleteRequestDTO,
                                           String username) throws IOException {

        CarImageCollection carImageCollection = carImageCollectionRepository.findById(collectionId)
                .orElseThrow(() -> new IOException("Car image collection with ID " + collectionId + " not found"));

        User owner = carImageCollection.getCar().getAdvert().getSeller();
        String ownerUsername = owner.getUsername();

        if (!Objects.equals(ownerUsername, username)) {
            throw new AdvertAddException("You are not the owner of this advert. Cannot delete images.");
        }

        List<String> carImageUrls = imageCollectionDeleteRequestDTO.getImageUrls();

        carImageCollection.getImages().removeIf(carImageUrl -> carImageUrls.contains(carImageUrl.getImageUrl()));
    }
}
