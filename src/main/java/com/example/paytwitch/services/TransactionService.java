package com.example.paytwitch.services;

import com.example.paytwitch.data.dtos.request.TransactionRequest;
import com.example.paytwitch.data.dtos.request.WalletRequest;
import com.example.paytwitch.data.dtos.response.TransactionReceiveResponse;
import com.example.paytwitch.data.dtos.response.TransactionResponse;

public interface TransactionService {

     TransactionResponse sendMoneyToAnotherUser(WalletRequest request, TransactionRequest transactionRequest);

     TransactionReceiveResponse userCanReceiveTheMoneySent(TransactionRequest transactionRequest);
}
