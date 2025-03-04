package com.example.ogiyo.domain.advertisement.entity;

import com.example.ogiyo.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    @Column(nullable = false)
    private LocalDateTime endedAt;

    @Column(nullable = false)
    private Status status;

    @OneToOne
    private Store store;

    public enum Status {
        ADVERTISING, NON_ADVERTISING;

        public static Status checkStatus(LocalDateTime startedAt, LocalDateTime endedAt) {
            if (startedAt.isAfter(LocalDateTime.now()) && endedAt.isBefore(LocalDateTime.now())) {
                return ADVERTISING;
            }
            return NON_ADVERTISING;
        }
    }
}
