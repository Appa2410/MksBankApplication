package com.bank.Application.dao;

import com.bank.Application.Model.AccountDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DashBoardRepository extends MongoRepository<AccountDetails, String> {

    AccountDetails findByEmail(String email);
}
