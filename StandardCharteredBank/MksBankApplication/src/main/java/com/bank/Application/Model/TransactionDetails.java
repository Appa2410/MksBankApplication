package com.bank.Application.Model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionDetails {

    private String date;

    private String transaction;

    private String currency;

    private String credit;

    private String debit;

    private double runningBalance;
}
