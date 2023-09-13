package com.bank.Application.Service;

import com.bank.Application.Model.*;
import com.bank.Application.dao.DashBoardRepository;
import com.bank.Application.dao.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService{

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    DashBoardRepository dashBoardRepository;

    @Override
    public TransactionResponse getTransactionDetails(Long accountNumber) {
        Transaction transaction = transactionRepository.findByAccountNumber(accountNumber);
        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setAccountNumber(accountNumber);
        if (transaction.getTransactionDetails().size()>0) {
            List<TransactionDetails> transactionDetails = transaction.getTransactionDetails().stream().sorted(Comparator.comparing(TransactionDetails::getDate).reversed()).limit(5).collect(Collectors.toList()).stream().sorted(Comparator.comparing(TransactionDetails::getDate)).collect(Collectors.toList());
            transactionDetails.stream().forEach(transactionDetails1 -> {
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(transactionDetails1.getDate());
                    String format = new SimpleDateFormat("dd-MMM").format(date);
                    transactionDetails1.setDate(format);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            });
            transactionResponse.setTransactionDetails(transactionDetails);
            transactionResponse.setCurrentBalance(transactionDetails.get(transactionDetails.size() - 1).getRunningBalance());
            transactionResponse.setAvailableBalance(transactionDetails.get(transactionDetails.size() - 1).getRunningBalance());
            transactionResponse.setMessage("Success");
        }
        else {
            List<AccountDetails> accountDetails = dashBoardRepository.findAll();
            Double currentBalance = accountDetails.stream().flatMap(i->i.getAccountSummary().stream().filter(j->j.getAccountNumber().equals(accountNumber)).map(k->k.getCurrentBalance())).findFirst().get();
            Double availableBalance = accountDetails.stream().flatMap(i->i.getAccountSummary().stream().filter(j->j.getAccountNumber().equals(accountNumber)).map(k->k.getAvailableBalance())).findFirst().get();
            transactionResponse.setCurrentBalance(currentBalance);
            transactionResponse.setAvailableBalance(availableBalance);
        }
        return transactionResponse;
    }
}
