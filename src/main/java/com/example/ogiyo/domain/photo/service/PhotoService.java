package com.example.ogiyo.domain.photo.service;

import com.example.ogiyo.domain.photo.domainType.DomainType;
import com.example.ogiyo.domain.photo.dto.PhotoUrlResponse;
import com.example.ogiyo.domain.photo.entity.Photo;
import com.example.ogiyo.domain.photo.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhotoService {
    private final PhotoRepository photoRepository;

    public int findMaxSeq(Long domainKey) {
        return photoRepository.findMaxSeqByDomainKey(domainKey);
    }

    public void save(Photo photo) {
        photoRepository.save(photo);
    }

    public void saveAll(List<Photo> photos) {
        photoRepository.saveAll(photos);
    }

    public void deleteByDomainTypeAndDomainKey(DomainType domainType, Long domainKey) {
        photoRepository.deleteByDomainTypeAndDomainKey(domainType, domainKey);
    }

    public List<PhotoUrlResponse> findByDomainTypeAndDomainKey(DomainType domainType, Long domainKey) {
        List<PhotoUrlResponse> photoUrls = photoRepository.findByDomainTypeAndDomainKey(domainType, domainKey)
                .stream()
                .map(photo -> new PhotoUrlResponse(photo.getPhotoUrl()))
                .toList();
        return photoUrls;
    }

}
