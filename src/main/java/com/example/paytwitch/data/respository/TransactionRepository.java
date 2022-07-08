package com.example.paytwitch.data.respository;

import com.example.paytwitch.data.models.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction,String> {
    Optional<Transaction> findTransactionBySender(String Sender);

    Optional<Transaction> findTransactionByReceiver(String receiver);
}
