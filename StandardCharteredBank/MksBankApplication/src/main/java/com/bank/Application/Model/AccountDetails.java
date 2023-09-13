package com.bank.Application.Model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "accountdetails")
public class AccountDetails {

    private String email;

    private List<AccountSummary> accountSummary = new ArrayList<>();

}
