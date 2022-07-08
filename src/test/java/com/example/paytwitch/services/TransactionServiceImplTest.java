package com.example.paytwitch.services;

import com.example.paytwitch.data.dtos.request.TransactionRequest;
import com.example.paytwitch.data.dtos.request.WalletRequest;
import com.example.paytwitch.data.dtos.response.TransactionReceiveResponse;
import com.example.paytwitch.data.dtos.response.TransactionResponse;
import com.example.paytwitch.data.models.WalletType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
class TransactionServiceImplTest {
    @Autowired
    private TransactionService transactionService;

    @Autowired
    WalletService walletService;


    @Test
    public void testThatUserWithWalletCanMakeTransaction(){
        //given that we have existing wallet
        WalletRequest newWallet = WalletRequest.builder()
                .Balance(BigDecimal.valueOf(20000.00))
                .userName("tolu@gmail.com")
                .wallet(WalletType.BASIC)
                .TransactionList(new ArrayList<>())
                .pin("5678")
                .transfer(20000)
                .TransactionList(new ArrayList<>())
                .build();
        walletService.createWallet(newWallet);
        TransactionRequest transactionRequest = TransactionRequest.builder()
                .date(LocalDate.now())
                .Sender("tolu@gmail.com")
                .receiver("layo@gmail.com")
                .amount(20000.00)
                .message("transaction made successfully")
                .build();
        TransactionResponse response = transactionService.sendMoneyToAnotherUser(newWallet,transactionRequest);

        assertEquals(20000.00,response.getAmount());
        assertEquals("tolu@gmail.com",response.getSender());

    }
    @Test
    public void testThatUserCanReceiveTheMoneySent(){
        WalletRequest newWallet2 = WalletRequest.builder()
                .Balance(BigDecimal.valueOf(00.00))
                .userName("layo@gmail.com")
                .wallet(WalletType.BASIC)
                .TransactionList(new ArrayList<>())
                .pin("6060")
                .build();
        walletService.createWallet(newWallet2);
        TransactionRequest transactionRequest = TransactionRequest.builder()
                .date(LocalDate.now())
                .Sender("tolu@gmail.com")
                .receiver("layo@gmail.com")
                .amount(20000.00)
                .message("receive payment successful")
                .build();
        TransactionReceiveResponse response = transactionService.userCanReceiveTheMoneySent(transactionRequest);

        assertEquals(20000.00,response.getAmount());
        assertEquals("layo@gmail.com",response.getReceiver());
    }
@Test
    public void testThatUserCanViewListOfTransactions(){
    WalletRequest newWallet2 = WalletRequest.builder()
            .Balance(BigDecimal.valueOf(00.00))
            .userName("layo@gmail.com")
            .wallet(WalletType.BASIC)
            .TransactionList(new ArrayList<>())
            .pin("6060")
            .build();
    walletService.createWallet(newWallet2);
    TransactionRequest transactionRequest = TransactionRequest.builder()
            .date(LocalDate.now())
            .Sender("tolu@gmail.com")
            .receiver("layo@gmail.com")
            .amount(20000.00)
            .message("receive payment successful")
            .build();
     transactionService.userCanReceiveTheMoneySent(transactionRequest);
//
//     ViewListOfTransactionRequest request = new ViewListOfTransactionRequest();
//     request

}

}