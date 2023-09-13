package com.bank.Application.Service;

import com.bank.Application.Model.TransactionResponse;

public interface TransactionService {

    TransactionResponse getTransactionDetails(Long accountNumber);
}
