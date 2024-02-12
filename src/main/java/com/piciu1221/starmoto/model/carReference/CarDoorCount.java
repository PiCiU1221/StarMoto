package com.piciu1221.starmoto.model.carReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "car_door_counts")
public class CarDoorCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "door_count_id")
    @NotNull
    private Integer doorId;

    @Column(name = "door_count", nullable = false, unique = true)
    @NotNull
    private Integer doorCount;
}