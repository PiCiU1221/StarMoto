package integration.com.piciu1221.starmoto.service;

import com.piciu1221.starmoto.StarMotoApplication;
import com.piciu1221.starmoto.service.CarImageService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = StarMotoApplication.class)
public class CarImageServiceIntegrationTest {
    @InjectMocks
    private CarImageService carImageService;

    @Test
    void uploadImage_RealApiCall_ReturnsUploadedImageUrl() throws IOException {
        // Provide a real image file for testing
        Path imagePath = Paths.get("src/test/resources/test.jpg");
        MockMultipartFile imageFile = new MockMultipartFile(
                "image", "test.jpg", "image/jpeg", Files.readAllBytes(imagePath)
        );

        // Perform the actual upload to Imgur API
        String imageUrl = carImageService.uploadImage(imageFile);

        // Assert that the URL is not null (indicating a successful upload)
        assertNotNull(imageUrl);

        // Print the image URL to the console
        System.out.println("Uploaded Image URL: " + imageUrl);
    }
}
