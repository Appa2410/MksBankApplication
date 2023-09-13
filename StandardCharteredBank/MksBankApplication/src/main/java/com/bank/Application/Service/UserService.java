package com.bank.Application.Service;

import com.bank.Application.Model.ForgotPasswordRequest;
import com.bank.Application.Model.ForgotRequest;
import com.bank.Application.Model.Login;
import com.bank.Application.Model.User;

public interface UserService {
    void newUser(User user);

    User userLogin(Login login);

    User forgotUserName(ForgotRequest forgotRequest);

    User forgotPassword(ForgotPasswordRequest forgotRequest);
}
