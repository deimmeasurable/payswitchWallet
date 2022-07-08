package com.example.paytwitch.services;


import com.example.paytwitch.data.dtos.request.CheckBalanceRequest;
import com.example.paytwitch.data.dtos.request.UpgradeWalletRequest;
import com.example.paytwitch.data.dtos.response.CheckBalanceResponse;
import com.example.paytwitch.data.dtos.response.CreditWalletResponse;
import com.example.paytwitch.data.dtos.request.CreditWalletRequest;
import com.example.paytwitch.data.dtos.request.WalletRequest;
import com.example.paytwitch.data.dtos.response.UpgradeWalletResponse;

import com.example.paytwitch.data.dtos.response.WalletDto;
import com.example.paytwitch.data.models.WalletType;
import com.example.paytwitch.data.respository.UserRepository;
import com.example.paytwitch.exception.UserNotFoundException;
import com.example.paytwitch.exception.WalletNotFoundException;
import com.example.paytwitch.exception.WalletPasswordWrongException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ImportAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
class WalletServiceImplTest {


    @Autowired
    private WalletService walletService;



    @BeforeEach
    void setUp() {
//        walletService = new WalletServiceImpl(userRepository);
//        userService = new UserServiceImpl();

    }
    @Test
    public void testThatWalletIsCreatedForNewUser(){

        WalletRequest newWallet = WalletRequest.builder()
                .Balance(00.0)
                .userName("tolu@gmail.com")
                .wallet(WalletType.BASIC)
                .TransactionList(new ArrayList<>())
                .pin("5678")
                .build();
        WalletDto walletDto = walletService.createWallet(newWallet);

        assertEquals("tolu@gmail.com",walletDto.getUserName());
        assertEquals("5678",walletDto.getPin());

    }
    @Test
    public void testThatBalanceCanBeCredit(){
        WalletRequest newWallet = WalletRequest.builder()
                .Balance(00.00)
                .userName("tolu@gmail.com")
                .wallet(WalletType.BASIC)
                .TransactionList(new ArrayList<>())
                .pin("5678")
                .build();
        walletService.createWallet(newWallet);

        CreditWalletRequest creditRequest = new CreditWalletRequest();
        creditRequest.setBalance(50000.00);
        creditRequest.setWallet(WalletType.BASIC);
        creditRequest.setTransactionList(new ArrayList<>());
        creditRequest.setUserName("tolu@gmail.com");
        creditRequest.setPin("5678");

        CreditWalletResponse creditBalanceResponse = walletService.creditUserBalance(creditRequest);
        assertEquals("tolu@gmail.com",creditBalanceResponse.getUserName());
        assertEquals("5678",creditBalanceResponse.getPin());
        assertEquals(50000.00,creditBalanceResponse.getBalance());
    }
    @Test
    public void testThatWalletCanBeUpgradedFromBasicToStandard() {
        WalletRequest newWallet = WalletRequest.builder()
                .Balance(00.00)
                .userName("tolu@gmail.com")
                .wallet(WalletType.BASIC)
                .TransactionList(new ArrayList<>())
                .pin("5678")
                .build();
        walletService.createWallet(newWallet);
        CreditWalletRequest creditRequest = new CreditWalletRequest();
        creditRequest.setBalance(50000.00);
        creditRequest.setTransactionList(new ArrayList<>());
        creditRequest.setUserName("tolu@gmail.com");
        creditRequest.setPin("5678");
         walletService.creditUserBalance(creditRequest);
         UpgradeWalletRequest upgradeRequest = new UpgradeWalletRequest();
         upgradeRequest.setWalletType(WalletType.STANDARD);
         upgradeRequest.setUserName("tolu@gmail.com");
         upgradeRequest.setMessage("wallet upgraded successfully");

         UpgradeWalletResponse upgradeWallet = walletService.upgradeUserWallet(upgradeRequest);

         assertEquals(WalletType.STANDARD, upgradeWallet.getWalletType());
         assertThat(upgradeWallet.getBalance()).isLessThanOrEqualTo(70000);
         assertEquals("wallet upgraded successfully",upgradeWallet.getMessage());


    }
    @Test
    public void testThatWalletTypeIsBasic(){
        WalletRequest newWallet = WalletRequest.builder()
                .Balance(00.00)
                .userName("tolu@gmail.com")
                .wallet(WalletType.BASIC)
                .TransactionList(new ArrayList<>())
                .pin("5678")
                .build();
        walletService.createWallet(newWallet);
        UpgradeWalletRequest upgradeRequest = new UpgradeWalletRequest();
        upgradeRequest.setWalletType(WalletType.BASIC);

        upgradeRequest.setUserName("tolu@gmail.com");
        upgradeRequest.setMessage("walletType is Basic");
        UpgradeWalletResponse upgradeWallet = walletService.userWalletTypeIsBasic(upgradeRequest);

        upgradeWallet.setWalletType(WalletType.BASIC);

        assertEquals(WalletType.BASIC, upgradeWallet.getWalletType());
       assertThat(upgradeWallet.getBalance()).isLessThan(50000.0);
        assertEquals("walletType is Basic",upgradeWallet.getMessage());


    }
    @Test
    public void testThatUserCanCheckBalanceInsideWallet(){
        WalletRequest newWallet2 = WalletRequest.builder()
                .Balance(00.00)
                .userName("layo@gmail.com")
                .wallet(WalletType.BASIC)
                .TransactionList(new ArrayList<>())
                .pin("6060")
                .build();
        walletService.createWallet(newWallet2);

        CheckBalanceRequest checkBalanceRequest = new CheckBalanceRequest();
        checkBalanceRequest.setUserName("layo@gmail.com");
        checkBalanceRequest.setPin("6060");
        checkBalanceRequest.setMessage("user view current balance");
        checkBalanceRequest.setDate(LocalDate.now());
        checkBalanceRequest.setBalance(00.00);

        CheckBalanceResponse checkBalanceResponse = walletService.userCanCheckCurrentBalance(checkBalanceRequest);

        assertEquals(00.00,checkBalanceResponse.getBalance());
        assertEquals("layo@gmail.com",checkBalanceResponse.getUserName());
        assertEquals("user view current balance",checkBalanceResponse.getMessage());
        assertEquals("6060",checkBalanceResponse.getPin());


    }
    @Test
    public void testThatExceptionIsThrownIfUserEnterAWrongPin(){
        WalletRequest newWallet2 = WalletRequest.builder()
                .Balance(00.00)
                .userName("layo@gmail.com")
                .wallet(WalletType.BASIC)
                .TransactionList(new ArrayList<>())
                .pin("6060")
                .build();

        walletService.createWallet(newWallet2);
        CreditWalletRequest creditRequest = new CreditWalletRequest();
        creditRequest.setBalance(50000.00);
        creditRequest.setWallet(WalletType.BASIC);
        creditRequest.setTransactionList(new ArrayList<>());
        creditRequest.setUserName("layo@gmail.com");
        creditRequest.setPin("6061");

        assertThrows(WalletPasswordWrongException.class,()->walletService.creditUserBalance(creditRequest));
    }
    @Test
    public void testThatExceptionIsThrownIfUserWithWrongUsername(){
        WalletRequest newWallet2 = WalletRequest.builder()
                .Balance(00.00)
                .userName("layo@gmail.com")
                .wallet(WalletType.BASIC)
                .TransactionList(new ArrayList<>())
                .pin("6060")
                .build();
        walletService.createWallet(newWallet2);
        CheckBalanceRequest checkBalanceRequest = new CheckBalanceRequest();
        checkBalanceRequest.setUserName("Tayo@gmail.com");
        checkBalanceRequest.setPin("6060");
        checkBalanceRequest.setMessage("user view current balance");
        checkBalanceRequest.setDate(LocalDate.now());
        checkBalanceRequest.setBalance(00.00);

        assertThrows(WalletNotFoundException.class,()-> walletService.userCanCheckCurrentBalance(checkBalanceRequest));

    }


}