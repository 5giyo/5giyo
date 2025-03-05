package com.example.ogiyo.domain.advertisement.entity;

import com.example.ogiyo.domain.store.entity.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
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

    public void updateStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        ADVERTISING, NON_ADVERTISING;

        public static Status checkStatus(LocalDateTime startedAt, LocalDateTime endedAt) {
            if (startedAt.isBefore(LocalDateTime.now()) && endedAt.isAfter(LocalDateTime.now())) {
                return ADVERTISING;
            }
            return NON_ADVERTISING;
        }
    }
}
