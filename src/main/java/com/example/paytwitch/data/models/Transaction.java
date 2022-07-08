package com.example.paytwitch.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    private String receiver;
    private String Sender;

    @Id
    private String TransactionId;
    private String amount;
    private LocalDate date = LocalDate.now();
    private String message;
}
