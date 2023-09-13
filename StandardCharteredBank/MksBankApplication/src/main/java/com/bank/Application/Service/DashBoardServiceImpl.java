package com.bank.Application.Service;

import com.bank.Application.Model.AccountDetails;
import com.bank.Application.Model.AccountSummary;
import com.bank.Application.dao.DashBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class DashBoardServiceImpl implements DashBoardService{

    @Autowired
    DashBoardRepository dashBoardRepository;

    @Override
    public List<AccountSummary> getSummaryDetails(String email) {
        AccountDetails accountDetails = dashBoardRepository.findByEmail(email);
        if(Objects.nonNull(accountDetails))
            return accountDetails.getAccountSummary();
        else
            return new ArrayList<>();
    }


}
