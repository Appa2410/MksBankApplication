package com.bank.Application.Model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "Transaction")
public class Transaction {

    private Long accountNumber;

    private List<TransactionDetails> transactionDetails;
}
