package com.example.ogiyo.domain.advertisement.repository;

import com.example.ogiyo.domain.advertisement.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    default Advertisement findByAdvertisementIdOrElseThrow(Long advertisementId) {
        return findById(advertisementId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Advertisement Not Found"));
    }
}
