package com.example.paytwitch.data.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckBalanceResponse {
    private String UserName;
    private String pin;
    private LocalDate date = LocalDate.now();
    private String message;
    private BigDecimal Balance;
}
