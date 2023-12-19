package com.piciu1221.starmoto.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class CarImageService {
    @Value("${imgur.clientId}")
    private String clientId;

    public String uploadImage(String base64Image) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", base64Image)
                .build();

        Request request = new Request.Builder()
                .url("https://api.imgur.com/3/image")
                .method("POST", body)
                .addHeader("Authorization", "Client-ID " + clientId)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                return extractImageUrlFromResponse(responseBody);
            } else {
                System.err.println("Error uploading image: " + response.code() + " - " + response.message());
                return null;
            }
        }
    }

    private String extractImageUrlFromResponse(String responseBody) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        // Assuming the response has a field named "link"
        return jsonNode.path("data").path("link").asText();
    }
}
