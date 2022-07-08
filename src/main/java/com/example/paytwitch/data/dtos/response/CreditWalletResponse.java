package com.example.paytwitch.data.dtos.response;

import com.example.paytwitch.data.models.Transaction;
import com.example.paytwitch.data.models.WalletType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreditWalletResponse {

    private BigDecimal Balance;
    private String pin;
    private String userName;
    private WalletType wallet;
    private List<Transaction> TransactionList = new ArrayList<>();
    private int transfer;
}
