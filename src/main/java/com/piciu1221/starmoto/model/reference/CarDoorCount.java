package com.piciu1221.starmoto.model.reference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "car_door_count")
public class CarDoorCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "door_count_id")
    private Integer doorId;

    @Column(name = "door_count", nullable = false, unique = true)
    private Integer count;
}