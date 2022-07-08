package com.example.paytwitch.data.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Wallet {
    private BigDecimal Balance;
    private String pin;
    @Id
    private String userName;
    private WalletType wallet;
    private List<Transaction> TransactionList = new ArrayList<>();
    private int transfer;
}
