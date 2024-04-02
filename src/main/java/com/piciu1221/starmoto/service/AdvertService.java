package com.piciu1221.starmoto.service;

import com.piciu1221.starmoto.dto.AdvertFilterDTO;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AdvertService {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final AdvertRepository advertRepository;

    private final CarService carService;

    @Transactional
    public AdvertResponseDTO addAdvert(
            @Valid AdvertPostRequestDTO advertPOSTRequestDTO,
            String username) {

        Advert advert = new Advert();

        User seller = userRepository.findByUsername(username)
                .orElseThrow(() -> new AdvertAddException("User with username: " + username + " not found"));
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

    public List<AdvertResponseDTO> getAllAdverts(AdvertFilterDTO advertFilterDTO,
                                                 int page,
                                                 int size) {
        if (size > 30) {
            size = 30;
        }

        Specification<Advert> spec = AdvertSpecificationBuilder.buildSpecification(advertFilterDTO);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "advertId"));

        Page<Advert> advertPage = advertRepository.findAll(spec, pageable);

        return advertPage.getContent().stream()
                .map(AdvertResponseDTO::new)
                .toList();
    }

    public AdvertResponseDTO getAdvertById(Long id) {
        Advert advert = advertRepository.findById(id)
                .orElseThrow(() -> new AdvertNotFoundException("Advert with ID " + id + " not found"));

        return new AdvertResponseDTO(advert);
    }

    @Transactional
    public AdvertResponseDTO updateAdvert(Long id,
                                          @Valid AdvertPostRequestDTO advertPOSTRequestDTO,
                                          String username) {
        Advert advert = advertRepository.findById(id)
                .orElseThrow(() -> new AdvertNotFoundException("Advert with ID " + id + " not found"));

        if (!Objects.equals(advert.getSeller().getUsername(), username)) {
            throw new AdvertAddException("You are not the owner of this advert. Cannot update.");
        }

        CarAddRequestDTO carAddRequestDTO = new CarAddRequestDTO(advertPOSTRequestDTO);
        Car car = carService.updateCar(advert.getCar().getCarId(), carAddRequestDTO);
        advert.setCar(car);

        advert.setTitle(advertPOSTRequestDTO.getTitle());
        advert.setDescription(advertPOSTRequestDTO.getDescription());

        Location location = locationRepository.findById(advertPOSTRequestDTO.getLocationId())
                .orElseThrow(() -> new AdvertAddException("Location with ID " + advertPOSTRequestDTO.getLocationId() + " not found"));
        advert.setLocation(location);

        advert.setPrice(advertPOSTRequestDTO.getPrice());

        // Phone numbers
        advert.getPhoneNumbers().clear();
        List<AdvertPhoneNumber> advertPhoneNumbers = new ArrayList<>();

        for (String phoneNumber : advertPOSTRequestDTO.getPhoneNumbers()) {
            AdvertPhoneNumber advertPhoneNumber = new AdvertPhoneNumber();
            advertPhoneNumber.setPhoneNumber(phoneNumber);
            advertPhoneNumber.setAdvert(advert);

            advertPhoneNumbers.add(advertPhoneNumber);
        }
        advert.getPhoneNumbers().addAll(advertPhoneNumbers);

        advert.setUpdatedAt(LocalDateTime.now());

        return new AdvertResponseDTO(advertRepository.save(advert));
    }

    @Transactional
    public void deleteAdvert(Long id, String username) {
        Advert advert = advertRepository.findById(id)
                .orElseThrow(() -> new AdvertNotFoundException("Advert with ID " + id + " not found"));

        if (!Objects.equals(advert.getSeller().getUsername(), username)) {
            throw new AdvertAddException("You are not the owner of this advert. Cannot delete.");
        }

        advertRepository.delete(advert);
    }
}
