package com.piciu1221.starmoto.model.reference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "car_makes")
public class CarMake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "make_id")
    private Integer makeId;

    @Column(name = "make_name", nullable = false, unique = true, length = 255)
    private String makeName;
}