package com.piciu1221.starmoto.model.advertReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long locationId;

    @ManyToOne
    @JoinColumn(name = "region_id")
    @NotNull
    private Region region;

    @Column(name = "postal_code", length = 6, nullable = false)
    @NotBlank
    private String postalCode;

    @Column(name = "city", length = 100, nullable = false)
    @NotBlank
    @Size(max = 100)
    private String city;
}