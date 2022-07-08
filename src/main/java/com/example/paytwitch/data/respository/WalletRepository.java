package com.example.paytwitch.data.respository;

import com.example.paytwitch.data.models.Transaction;
import com.example.paytwitch.data.models.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends MongoRepository<Wallet,String> {
    Optional<Wallet> findWalletByUserName(String username);
}
