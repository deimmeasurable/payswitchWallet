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
import com.example.paytwitch.exception.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface WalletService {
    WalletDto createWallet(WalletRequest newWallet);

    CreditWalletResponse creditUserBalance(CreditWalletRequest creditRequest);

    UpgradeWalletResponse upgradeUserWallet(UpgradeWalletRequest upgradeRequest);

    UpgradeWalletResponse userWalletTypeIsBasic(UpgradeWalletRequest upgradeRequest);

    CheckBalanceResponse userCanCheckCurrentBalance(CheckBalanceRequest checkBalanceRequest);
}
