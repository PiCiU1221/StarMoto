package com.piciu1221.starmoto.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class ImageCollectionDeleteRequestDTO {
    @NotEmpty(message = "Image URLs list cannot be empty")
    private List<String> imageUrls;
}
