package com.piciu1221.starmoto.model.carReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Data
@Table(name = "car_seat_counts")
public class CarSeatCount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_count_id")
    private Long seatCountId;

    @Column(name = "seat_count", nullable = false, unique = true)
    @NotNull
    private Integer seatCount;
}
