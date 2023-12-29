package com.piciu1221.starmoto.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

@Service
public class CarImageService {
    @Value("${imgur.clientId}")
    private String clientId;

    public String uploadImage(MultipartFile imageFile) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Compress the image
        byte[] compressedImageBytes = compressImage(imageFile.getBytes());

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", imageFile.getOriginalFilename(), RequestBody.create(MediaType.parse("image/*"), compressedImageBytes))
                .build();

        Request request = new Request.Builder()
                .url("https://api.imgur.com/3/image")
                .header("Authorization", "Client-ID " + clientId)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return extractImageUrlFromResponse(responseBody);
            } else {
                System.err.println("Error uploading image: " + response.code() + " - " + response.message());
                // Handle error or log it
                return null;
            }
        }
    }

    private String extractImageUrlFromResponse(String responseBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        return jsonNode.path("data").path("link").asText();
    }

    private byte[] compressImage(byte[] originalImageBytes) throws IOException {
        // Convert the original image bytes to BufferedImage
        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(originalImageBytes));

        // Set the compression quality based on the original image size
        float compressionQuality = calculateCompressionQuality(originalImageBytes.length);

        // Create a ByteArrayOutputStream to hold the compressed image bytes
        ByteArrayOutputStream compressedImageOutputStream = new ByteArrayOutputStream();

        // Get all available ImageWriters
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (writers.hasNext()) {
            ImageWriter writer = writers.next();
            ImageWriteParam writeParam = writer.getDefaultWriteParam();

            // Set compression quality
            writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            writeParam.setCompressionQuality(compressionQuality);

            // Write the compressed image bytes to the ByteArrayOutputStream
            try (ImageOutputStream ios = ImageIO.createImageOutputStream(compressedImageOutputStream)) {
                writer.setOutput(ios);
                writer.write(null, new IIOImage(originalImage, null, null), writeParam);
            } finally {
                writer.dispose();
            }
        }

        return compressedImageOutputStream.toByteArray();
    }

    private float calculateCompressionQuality(int originalImageSize) {
        // Set a threshold for image size above which we apply more compression
        int sizeThreshold = 10 * 1024 * 1024; // 10 MB

        // Set the initial compression quality
        float initialCompressionQuality = 0.8f;

        // Adjust compression quality based on image size
        if (originalImageSize > sizeThreshold) {
            // If the original image is larger than 10MB, increase compression to reduce size
            return 0.5f;
        } else {
            // Otherwise, use the initial compression quality
            return initialCompressionQuality;
        }
    }
}
