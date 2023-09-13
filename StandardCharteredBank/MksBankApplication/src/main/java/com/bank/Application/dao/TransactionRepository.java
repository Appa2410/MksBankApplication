package com.bank.Application.dao;

import com.bank.Application.Model.Transaction;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends MongoRepository<Transaction, String> {

    Transaction findByAccountNumber(Long accountNumber);

    /*@Query(value = "{accountNumber:?0}", sort = "{date:-1}")
    Transaction getByAccNumAndSortedByDate(Long accountNumber);*/
}
