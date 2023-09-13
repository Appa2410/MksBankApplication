package com.bank.Application.Model;

import lombok.Data;

@Data
public class AccountSummary {

    private String accountType;

    private Long accountNumber;

    private double availableBalance;

    private double currentBalance;
}
