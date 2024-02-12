package com.piciu1221.starmoto.model.carReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "car_colors")
public class CarColor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "color_id")
    @NotNull
    private Integer colorId;

    @Column(name = "color_name", nullable = false, unique = true)
    @NotBlank
    private String colorName;
}
