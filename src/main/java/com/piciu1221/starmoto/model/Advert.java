package com.piciu1221.starmoto.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.piciu1221.starmoto.model.advertReference.AdvertPhoneNumber;
import com.piciu1221.starmoto.model.advertReference.Location;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "adverts")
public class Advert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "advert_id")
    private Long advertId;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    @NotNull
    private User seller;

    @OneToOne
    @JoinColumn(name = "car_id", nullable = false)
    @NotNull
    private Car car;

    @Column(name = "title", nullable = false)
    @NotNull
    private String title;

    @Column(name = "description", nullable = false)
    @NotNull
    private String description;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    @NotNull
    private Location location;

    @NotNull
    @Positive
    private Integer price;

    @Column(name = "created_at", nullable = false, updatable = false)
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "updated_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedAt = null;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "advert_id")
    private List<AdvertPhoneNumber> phoneNumbers;
}
