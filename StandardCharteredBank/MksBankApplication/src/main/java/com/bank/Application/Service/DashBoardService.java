package com.bank.Application.Service;

import com.bank.Application.Model.AccountSummary;

import java.util.List;

public interface DashBoardService {
    List<AccountSummary> getSummaryDetails(String email);
}
