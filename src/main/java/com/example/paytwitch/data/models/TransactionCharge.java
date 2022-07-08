package com.example.paytwitch.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionCharge {
    private int AmountIncurred;
    private List<WalletType> walletType= new ArrayList<>();
    private List<Wallet> wallet=new ArrayList<>();
    private List<Transaction> transactions=new ArrayList<>();
}
