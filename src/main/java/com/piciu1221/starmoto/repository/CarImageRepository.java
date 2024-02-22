package com.piciu1221.starmoto.repository;

import com.piciu1221.starmoto.model.carReference.CarImageUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarImageRepository extends JpaRepository<CarImageUrl, Long> {
}
