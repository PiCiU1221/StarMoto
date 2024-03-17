package com.piciu1221.starmoto.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ImageCollectionPostRequest {
    @NotNull(message = "Images are required")
    @NotEmpty(message = "Images list cannot be empty")
    private List<MultipartFile> images;
}
