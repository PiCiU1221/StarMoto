package com.piciu1221.starmoto.model.advertReference;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.piciu1221.starmoto.model.Advert;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
@Table(name = "advert_phone_numbers")
public class AdvertPhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_number_id")
    private Long phoneNumberId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "advert_id")
    @NotNull
    private Advert advert;

    @Column(name = "phone_number", length = 9, nullable = false)
    @NotBlank
    @Size(max = 9)
    private String phoneNumber;
}
