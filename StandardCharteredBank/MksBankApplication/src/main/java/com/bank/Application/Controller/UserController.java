package com.bank.Application.Controller;

import com.bank.Application.Exception.EmailAlreadyExistsException;
import com.bank.Application.Exception.PasswordInValidException;
import com.bank.Application.Exception.PasswordIncorrectException;
import com.bank.Application.Exception.UserNotFoundException;
import com.bank.Application.Model.*;
import com.bank.Application.Service.DashBoardService;
import com.bank.Application.Service.EmailService;
import com.bank.Application.Service.PasswordEncryptDecryptServiceImpl;
import com.bank.Application.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/mks-bank")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private DashBoardService dashBoardService;

    @Autowired
    PasswordEncryptDecryptServiceImpl passwordEncryptDecryptService;

    @PostMapping(value = "/sign-up")
    public ResponseEntity<String> newUser(@Valid @RequestBody User user){
        try {
            userService.newUser(user);
            emailService.sendEmail(user,"register");
        } catch (EmailAlreadyExistsException e){
            return new ResponseEntity<>("Email Id "+user.getEmail()+" is already registered with us. Please try to LogIn",HttpStatus.NOT_FOUND);
        }catch (PasswordInValidException e){
            return new ResponseEntity<>("Password doesn't meet the requirements. Please try different one",HttpStatus.BAD_REQUEST);
        }
        catch (Exception e){
            return new ResponseEntity<>("Invalid data", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("User added successfully.",HttpStatus.OK);
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity loginUser(@Valid @RequestBody Login lo) throws Exception {
        User user;
        try {
             user = userService.userLogin(lo);
        }
        catch (UserNotFoundException e){
            return new ResponseEntity<>("{\"message\":\"Invalid user name\"}",HttpStatus.OK);
        }catch (PasswordIncorrectException e){
            return new ResponseEntity<>("{\"message\":\"Incorrect Password\"}",HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("{\"message\":\"Invalid data\"}", HttpStatus.BAD_REQUEST);
        }
        List<AccountSummary> accountSummaryList = dashBoardService.getSummaryDetails(user.getEmail());
        return new ResponseEntity<>(accountSummaryList,HttpStatus.OK);
    }

    @PostMapping("/forgot-username")
    public ResponseEntity<String> forgotUserName(@Valid @RequestBody ForgotRequest forgotRequest) throws Exception{
        try {
            User user = userService.forgotUserName(forgotRequest);
            emailService.sendUserName(forgotRequest,user,"forgotUserName");
        }
        catch(UserNotFoundException e){
            return new ResponseEntity<>("Email Id "+forgotRequest.getEmail()+" not found, Please provide the valid email",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("User Name has been sent successfully", HttpStatus.OK);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgotPasswordRequest forgotRequest) throws Exception{
        try {
            User user = userService.forgotPassword(forgotRequest);
            String password = passwordEncryptDecryptService.passwordDecryption(user.getPassword());
            user.setPassword(password);
            emailService.sendPassword(forgotRequest,user,"forgotPassword");
        }
        catch(UserNotFoundException e){
            return new ResponseEntity<>("Email Id "+forgotRequest.getEmail()+" not found, Please provide the valid email.",HttpStatus.NOT_FOUND);
        }
        catch (PasswordInValidException e){
            return new ResponseEntity<>("User Name "+forgotRequest.getUserName()+" not found, Please provide the valid user name.",HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Password has been sent successfully", HttpStatus.OK);
    }

}