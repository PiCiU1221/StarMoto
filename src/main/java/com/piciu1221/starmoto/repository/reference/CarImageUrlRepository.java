package com.piciu1221.starmoto.repository.reference;

import com.piciu1221.starmoto.model.reference.CarImageUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarImageUrlRepository extends JpaRepository<CarImageUrl, Integer> {
    boolean existsByImageUrl(String imageUrl);
}
