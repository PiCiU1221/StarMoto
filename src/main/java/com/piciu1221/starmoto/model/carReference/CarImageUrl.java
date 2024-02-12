package com.piciu1221.starmoto.model.carReference;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.piciu1221.starmoto.model.Car;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "car_image_urls")
public class CarImageUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    @NotNull
    private Integer imageId;

    @ManyToOne
    @JoinColumn(name = "car_id")
    @NotNull
    private Car car;

    @Column(name = "image_url", nullable = false)
    @NotBlank
    private String imageUrl;

    @Column(name = "created_at", nullable = false)
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();
}
