package com.bank.Application.Controller;

import com.bank.Application.Model.TransactionResponse;
import com.bank.Application.Service.TransactionService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/mksBank")
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @GetMapping(value = "/getTransaction/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getTransaction(@PathVariable Long accountNumber) {
        TransactionResponse transactionResponse = transactionService.getTransactionDetails(accountNumber);
        return new ResponseEntity<>(transactionResponse, HttpStatus.OK);
    }
}
