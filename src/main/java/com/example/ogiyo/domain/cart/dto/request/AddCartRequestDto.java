package com.example.ogiyo.domain.cart.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AddCartRequestDto {
    private long menuId;
    @NotNull
    private int quantity;
}
