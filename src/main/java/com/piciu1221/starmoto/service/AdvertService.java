package com.piciu1221.starmoto.service;

import com.piciu1221.starmoto.dto.AdvertPostRequestDTO;
import com.piciu1221.starmoto.dto.AdvertResponseDTO;
import com.piciu1221.starmoto.dto.CarAddRequestDTO;
import com.piciu1221.starmoto.exception.AdvertAddException;
import com.piciu1221.starmoto.exception.AdvertNotFoundException;
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
    public AdvertResponseDTO addAdvert(@Valid AdvertPostRequestDTO advertPOSTRequestDTO) throws IOException {

        Advert advert = new Advert();

        User seller = userRepository.findById(advertPOSTRequestDTO.getSellerId())
                .orElseThrow(() -> new AdvertAddException("Seller with ID " + advertPOSTRequestDTO.getSellerId() + " not found"));
        advert.setSeller(seller);

        CarAddRequestDTO carAddRequestDTO = new CarAddRequestDTO(advertPOSTRequestDTO);
        Car car = carService.addCar(carAddRequestDTO);
        advert.setCar(car);

        advert.setTitle(advertPOSTRequestDTO.getTitle());
        advert.setDescription(advertPOSTRequestDTO.getDescription());

        Location location = locationRepository.findById(advertPOSTRequestDTO.getLocationId())
                .orElseThrow(() -> new AdvertAddException("Location with ID " + advertPOSTRequestDTO.getLocationId() + " not found"));
        advert.setLocation(location);

        advert.setPrice(advertPOSTRequestDTO.getPrice());

        List<AdvertPhoneNumber> advertPhoneNumbers = new ArrayList<>();

        for (String phoneNumber : advertPOSTRequestDTO.getPhoneNumbers()) {
            AdvertPhoneNumber advertPhoneNumber = new AdvertPhoneNumber();
            advertPhoneNumber.setPhoneNumber(phoneNumber);
            advertPhoneNumber.setAdvert(advert);

            advertPhoneNumbers.add(advertPhoneNumber);
        }

        advert.setPhoneNumbers(advertPhoneNumbers);

        return new AdvertResponseDTO(advertRepository.save(advert));
    }

    public AdvertResponseDTO getAdvertById(Long id) {
        Advert advert = advertRepository.findById(id)
                .orElseThrow(() -> new AdvertNotFoundException("Advert with ID " + id + " not found"));

        return new AdvertResponseDTO(advert);
    }
}
