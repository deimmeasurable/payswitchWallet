package com.example.paytwitch.services;


import com.example.paytwitch.data.dtos.request.CheckBalanceRequest;
import com.example.paytwitch.data.dtos.request.CreditWalletRequest;
import com.example.paytwitch.data.dtos.request.UpgradeWalletRequest;
import com.example.paytwitch.data.dtos.request.WalletRequest;
import com.example.paytwitch.data.dtos.response.CheckBalanceResponse;
import com.example.paytwitch.data.dtos.response.CreditWalletResponse;
import com.example.paytwitch.data.dtos.response.UpgradeWalletResponse;
import com.example.paytwitch.data.dtos.response.WalletDto;
import com.example.paytwitch.data.models.Wallet;
import com.example.paytwitch.data.models.WalletType;
import com.example.paytwitch.data.respository.UserRepository;
import com.example.paytwitch.data.respository.WalletRepository;
import com.example.paytwitch.exception.WalletNotFoundException;
import com.example.paytwitch.exception.WalletPasswordWrongException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletServiceImpl implements WalletService {
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WalletRepository walletRepository;

//        public WalletServiceImpl(UserRepository userRepository){
//            this.userRepository=userRepository;
//        }


    @Override
    public WalletDto createWallet(WalletRequest newWallet) {

        Wallet wallet = new Wallet();
        wallet.setUserName(newWallet.getUserName());
        wallet.setBalance(newWallet.getBalance());
        wallet.setPin(newWallet.getPin());
        wallet.setTransfer(newWallet.getTransfer());
        wallet.setWallet(newWallet.getWallet());
        walletRepository.save(wallet);


        WalletDto walletDto = new WalletDto();
        walletDto.setUserName(wallet.getUserName());
        walletDto.setPin(wallet.getPin());
        wallet.setTransfer(wallet.getTransfer());

        walletDto.setBalance(wallet.getBalance());


        return walletDto;

    }

    @Override
    public CreditWalletResponse creditUserBalance(CreditWalletRequest creditRequest) {
        Optional<Wallet> foundWallet = walletRepository.findWalletByUserName(creditRequest.getUserName());
        if (foundWallet.isEmpty()) {
            throw new WalletNotFoundException("wallet not found, because user doesn't exist");

        }
        if (!creditRequest.getPin().equals(foundWallet.get().getPin())) {
            throw new WalletPasswordWrongException("password does not exist");
        }
        foundWallet.get().setBalance(creditRequest.getBalance());
        Wallet savedFoundWallet = walletRepository.save(foundWallet.get());
        CreditWalletResponse creditWalletresponse = new CreditWalletResponse();
        creditWalletresponse.setBalance(creditRequest.getBalance());
        creditWalletresponse.setUserName(creditRequest.getUserName());
        creditWalletresponse.setPin(creditRequest.getPin());
        creditWalletresponse.setTransactionList(creditRequest.getTransactionList());
        creditWalletresponse.setTransfer(creditRequest.getTransfer());
        System.out.println(savedFoundWallet);

        return creditWalletresponse;

    }

    @Override
    public UpgradeWalletResponse upgradeUserWallet(UpgradeWalletRequest upgradeRequest) {
        Optional<Wallet> foundWallet = walletRepository.findWalletByUserName(upgradeRequest.getUserName());
        if (foundWallet.isEmpty()) {
            throw new WalletNotFoundException("wallet not found, because user doesn't exist");
        }
        if (foundWallet.get().getBalance().compareTo(BigDecimal.valueOf(49000.00))>0 && foundWallet.get().getBalance().compareTo(BigDecimal.valueOf(100000.00))>0){
            foundWallet.get().setWallet(WalletType.STANDARD);
        }
        Wallet savedWallet = walletRepository.save(foundWallet.get());
        UpgradeWalletResponse upgradeWalletResponse = new UpgradeWalletResponse();
        upgradeWalletResponse.setWalletType(savedWallet.getWallet());
        upgradeWalletResponse.setUserName(savedWallet.getUserName());
        upgradeWalletResponse.setBalance(savedWallet.getBalance());
        upgradeWalletResponse.setMessage(upgradeRequest.getMessage());


        return upgradeWalletResponse;

    }

    @Override
    public UpgradeWalletResponse userWalletTypeIsBasic(UpgradeWalletRequest upgradeRequest) {
        Optional<Wallet> wallet = walletRepository.findWalletByUserName(upgradeRequest.getUserName());
        if (wallet.isEmpty()) {
            throw new WalletNotFoundException("wallet not found, because user doesn't exist");
        }
        if (wallet.get().getBalance().compareTo(BigDecimal.valueOf(00.00))>0  && wallet.get().getBalance().compareTo(BigDecimal.valueOf(49000.00))>=49000.00) {
            wallet.get().setWallet(WalletType.BASIC);
        }
        Wallet newWalletStatus = walletRepository.save(wallet.get());

        UpgradeWalletResponse upgradeWalletResponse = new UpgradeWalletResponse();
        upgradeWalletResponse.setWalletType(newWalletStatus.getWallet());
        upgradeWalletResponse.setUserName(newWalletStatus.getUserName());
        upgradeWalletResponse.setBalance(newWalletStatus.getBalance());
        upgradeWalletResponse.setMessage(upgradeRequest.getMessage());

        return upgradeWalletResponse;

    }

    @Override
    public CheckBalanceResponse userCanCheckCurrentBalance(CheckBalanceRequest checkBalanceRequest) {
        Optional<Wallet> foundWallet = walletRepository.findWalletByUserName(checkBalanceRequest.getUserName());
        if (foundWallet.isEmpty()) {
            throw new WalletNotFoundException("wallet not found, because user doesn't exist");

        }
        if (!checkBalanceRequest.getPin().equals(foundWallet.get().getPin())) {
            throw new WalletPasswordWrongException("password does not exist");
        }
        CheckBalanceResponse response = new CheckBalanceResponse();
        response.setBalance(foundWallet.get().getBalance());
        response.setUserName(foundWallet.get().getUserName());
        response.setPin(foundWallet.get().getPin());
        response.setMessage(checkBalanceRequest.getMessage());

        return response;

    }
}
