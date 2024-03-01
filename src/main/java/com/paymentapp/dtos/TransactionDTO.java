package com.paymentapp.dtos;

import com.paymentapp.domain.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionDTO(BigDecimal value, User payer, User payee, LocalDateTime timestemp) {
}
