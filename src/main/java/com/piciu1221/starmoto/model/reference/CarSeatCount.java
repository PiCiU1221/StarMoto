package com.piciu1221.starmoto.model.reference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "car_seat_count")
public class CarSeatCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_count_id")
    private Integer seatsId;

    @Column(name = "seat_count", nullable = false, unique = true)
    private Integer seatsCount;
}
