package com.example.paytwitch.services;

import com.example.paytwitch.data.dtos.request.TransactionRequest;
import com.example.paytwitch.data.dtos.request.WalletRequest;
import com.example.paytwitch.data.dtos.response.TransactionReceiveResponse;
import com.example.paytwitch.data.dtos.response.TransactionResponse;
import com.example.paytwitch.data.models.Transaction;
import com.example.paytwitch.data.models.Wallet;
import com.example.paytwitch.data.respository.TransactionRepository;
import com.example.paytwitch.data.respository.WalletRepository;
import com.example.paytwitch.exception.WalletNotFoundException;
import com.example.paytwitch.exception.WalletPasswordWrongException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    WalletRepository walletRepository;

    @Autowired
    TransactionRepository transactionRepository;


    @Override
    public TransactionResponse sendMoneyToAnotherUser(WalletRequest walletRequest,TransactionRequest transactionRequest) {
        Optional<Wallet> foundWallet = walletRepository.findWalletByUserName(walletRequest.getUserName());
        if (foundWallet.isEmpty()) {
            throw new WalletNotFoundException(foundWallet.get().getUserName()+ ",wallet not found, because user doesn't exist");

        }
        if (!walletRequest.getPin().equals(foundWallet.get().getPin())) {
            throw new WalletPasswordWrongException("password does not exist");
        }
        Optional<Wallet> secondUserWallet= walletRepository.findWalletByUserName(transactionRequest.getReceiver());
        if(secondUserWallet.isEmpty()){
            throw new WalletNotFoundException(secondUserWallet.get().getUserName()+ ",wallet not found, because user doesn't exist");
        }
        if(foundWallet.get().getBalance().compareTo(new BigDecimal("00.00")) > 0){
           foundWallet.get().setTransfer((int) transactionRequest.getAmount());
        }
        Wallet savedFoundedWallet = walletRepository.save(foundWallet.get());

        Transaction transaction = new Transaction();
        transaction.setAmount(String.valueOf(transactionRequest.getAmount()));
        transaction.setDate(transactionRequest.getDate());
        transaction.setSender(transactionRequest.getSender());
        transaction.setReceiver(transactionRequest.getReceiver());
        transaction.setMessage(transactionRequest.getMessage());
        transaction.setTransactionId(transactionRequest.getTransactionId());

        savedFoundedWallet.getTransactionList().add(transaction);
        walletRepository.save(savedFoundedWallet);
        BigDecimal remainingBalance = BigDecimal.valueOf(Long.parseLong(String.valueOf(savedFoundedWallet.getBalance().subtract(BigDecimal.valueOf(foundWallet.get().getTransfer())))));
        savedFoundedWallet.setBalance(remainingBalance);
//
        secondUserWallet.get().setBalance(BigDecimal.valueOf(savedFoundedWallet.getTransfer()));
         walletRepository.save(secondUserWallet.get());
        TransactionResponse response = new TransactionResponse();
        response.setAmount(BigDecimal.valueOf(transactionRequest.getAmount()));
        response.setDate(transactionRequest.getDate());
        response.setSender(savedFoundedWallet.getUserName());
        response.setReceiver(secondUserWallet.get().getUserName());
        response.setTransactionId(transactionRequest.getTransactionId());

        System.out.println(savedFoundedWallet);
        System.out.println(secondUserWallet);



        return response;
    }

    @Override
    public TransactionReceiveResponse userCanReceiveTheMoneySent(TransactionRequest transactionRequest) {
        Optional<Wallet> foundWallet= walletRepository.findWalletByUserName(transactionRequest.getReceiver());

        if(foundWallet.isEmpty()){
            throw new WalletNotFoundException(foundWallet.get().getUserName() +"wallet not found, because user doesn't exist");
        }
        Transaction transactionReceive = new Transaction();
        transactionReceive.setAmount(String.valueOf(transactionRequest.getAmount()));
        transactionReceive.setDate(transactionRequest.getDate());
        transactionReceive.setSender(transactionRequest.getSender());
        transactionReceive.setReceiver(transactionRequest.getReceiver());
        transactionReceive.setMessage(transactionRequest.getMessage());
        transactionReceive.setTransactionId(transactionRequest.getTransactionId());

        foundWallet.get().setBalance(BigDecimal.valueOf(transactionRequest.getAmount()));

        walletRepository.save(foundWallet.get());

        foundWallet.get().getTransactionList().add(transactionReceive);
        walletRepository.save(foundWallet.get());

        TransactionReceiveResponse response = new TransactionReceiveResponse();
        response.setAmount(BigDecimal.valueOf(transactionRequest.getAmount()));
        response.setDate(transactionRequest.getDate());
        response.setSender(transactionRequest.getSender());
        response.setReceiver(transactionRequest.getReceiver());
        response.setTransactionId(transactionRequest.getTransactionId());

        System.out.println(foundWallet);


        return response;
    }
}
