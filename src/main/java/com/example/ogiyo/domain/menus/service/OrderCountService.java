package com.example.ogiyo.domain.menus.service;
import com.example.ogiyo.domain.menus.repository.OrderCountRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class OrderCountService {

    private final OrderCountRepository orderCountRepository;

    @Transactional
    public void increaseOrderCount(Long orderCountId) {
        orderCountRepository.increaseOrderCount(orderCountId);
    }
}

