package com.example.paytwitch.data.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionRequest {
    private String receiver;
    private String Sender;

    @Id
    private String transactionId;
    private double amount;
    private LocalDate date = LocalDate.now();
    private String message;
}
