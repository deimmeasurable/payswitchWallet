package com.example.paytwitch.data.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionReceiveResponse {
    private String receiver;
    private String Sender;

    @Id
    private String TransactionId;
    private BigDecimal amount;
    private LocalDate date = LocalDate.now();
    private String message;
}
