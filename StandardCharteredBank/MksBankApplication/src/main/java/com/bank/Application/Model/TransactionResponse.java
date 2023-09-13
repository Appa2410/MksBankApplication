package com.bank.Application.Model;

import lombok.Data;
import org.apache.catalina.LifecycleState;

import java.util.ArrayList;
import java.util.List;

@Data
public class TransactionResponse {

    private Long accountNumber;

    private List<TransactionDetails> transactionDetails = new ArrayList<>();

    private double currentBalance;

    private double availableBalance;

    private String message;
}
