package com.example.ogiyo.domain.order.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class AddOrderRequestDto {

    @NotBlank
    private int totalPrice;
    @NotBlank
    private String paymentMethod;
}
