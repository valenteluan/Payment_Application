package com.paymentapp.dtos;

import java.math.BigDecimal;

public record TransactionDTO(
        BigDecimal amount,
        Long payer_id,
        Long payee_id
) {
}
