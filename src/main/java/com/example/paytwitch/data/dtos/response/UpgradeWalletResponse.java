package com.example.paytwitch.data.dtos.response;

import com.example.paytwitch.data.models.WalletType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpgradeWalletResponse {
    private String message;
    private BigDecimal balance;
    private WalletType walletType;
    private String userName;
}
