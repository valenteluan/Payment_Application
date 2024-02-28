package com.paymentapp.dtos;

import com.paymentapp.domain.user.UserType;

public record UserDTO(

        String name,
        Integer cpf,
        String email,
        String password,
        UserType userType
) {
}
