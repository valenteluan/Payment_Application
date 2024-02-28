package com.paymentapp.dtos;

import com.paymentapp.domain.user.UserType;

import java.math.BigDecimal;

public record UserDTO(

        String name,
        String cpf,
        String email,
        String password,
        UserType userType,
        BigDecimal amount
) {
}
