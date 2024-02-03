package com.piciu1221.starmoto.model.reference;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "car_conditions")
public class CarCondition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "condition_id")
    private Integer conditionId;

    @Column(name = "condition_name", nullable = false, unique = true, length = 255)
    private String conditionName;
}