package com.bank.Application.Model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
@Data
public class Login {
    @NotEmpty(message = "userName is mandatory")
    private String userName;

    @NotEmpty(message = "Password is mandatory")
    private String password;
    
}
