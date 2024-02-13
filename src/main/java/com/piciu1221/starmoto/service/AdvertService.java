package com.piciu1221.starmoto.service;

import com.piciu1221.starmoto.dto.AdvertAddRequestDTO;
import com.piciu1221.starmoto.dto.AdvertAddResponseDTO;
import com.piciu1221.starmoto.dto.CarAddRequestDTO;
import com.piciu1221.starmoto.exception.AdvertAddException;
import com.piciu1221.starmoto.model.Advert;
import com.piciu1221.starmoto.model.Car;
import com.piciu1221.starmoto.model.User;
import com.piciu1221.starmoto.model.advertReference.AdvertPhoneNumber;
import com.piciu1221.starmoto.model.advertReference.Location;
import com.piciu1221.starmoto.repository.AdvertRepository;
import com.piciu1221.starmoto.repository.UserRepository;
import com.piciu1221.starmoto.repository.advertReference.LocationRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdvertService {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final AdvertRepository advertRepository;

    private final CarService carService;

    @Transactional
    public AdvertAddResponseDTO addAdvert(@Valid AdvertAddRequestDTO advertAddRequestDTO) throws IOException {

        Advert advert = new Advert();

        User seller = userRepository.findById(advertAddRequestDTO.getSellerId())
                .orElseThrow(() -> new AdvertAddException("Seller with ID " + advertAddRequestDTO.getSellerId() + " not found"));
        advert.setSeller(seller);

        CarAddRequestDTO carAddRequestDTO = new CarAddRequestDTO(advertAddRequestDTO);
        Car car = carService.addCar(carAddRequestDTO);
        advert.setCar(car);

        advert.setTitle(advertAddRequestDTO.getTitle());
        advert.setDescription(advertAddRequestDTO.getDescription());

        Location location = locationRepository.findById(advertAddRequestDTO.getLocationId())
                .orElseThrow(() -> new AdvertAddException("Location with ID " + advertAddRequestDTO.getLocationId() + " not found"));
        advert.setLocation(location);

        advert.setPrice(advertAddRequestDTO.getPrice());

        List<AdvertPhoneNumber> advertPhoneNumbers = new ArrayList<>();

        for (String phoneNumber : advertAddRequestDTO.getPhoneNumbers()) {
            AdvertPhoneNumber advertPhoneNumber = new AdvertPhoneNumber();
            advertPhoneNumber.setPhoneNumber(phoneNumber);
            advertPhoneNumber.setAdvert(advert);

            advertPhoneNumbers.add(advertPhoneNumber);
        }

        advert.setPhoneNumbers(advertPhoneNumbers);

        return new AdvertAddResponseDTO(advertRepository.save(advert));
    }
}
