package com.example.paytwitch.data.respository;

import com.example.paytwitch.data.models.TransactionCharge;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionChargeRepository extends MongoRepository<TransactionCharge,String> {

}
