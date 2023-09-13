package com.bank.Application.Service;

import com.bank.Application.Exception.EmailAlreadyExistsException;
import com.bank.Application.Exception.UserNotFoundException;
import com.bank.Application.Exception.PasswordInValidException;
import com.bank.Application.Exception.PasswordIncorrectException;
import com.bank.Application.Model.ForgotPasswordRequest;
import com.bank.Application.Model.ForgotRequest;
import com.bank.Application.Model.Login;
import com.bank.Application.Model.User;
import com.bank.Application.dao.UserRepository;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncryptDecryptServiceImpl passwordEncryptDecryptService;

    @Override
    public void newUser(User user) {
        Faker faker = new Faker();
        String userName = user.getFirstName().substring(0,1)+user.getLastName().substring(0,1)+faker.address().buildingNumber();
        user.setUserName(userName);
        Optional<User> reg = Optional.ofNullable(userRepository.findByEmail(user.getEmail()));
        if(reg.isPresent()) {
            throw new EmailAlreadyExistsException("Email Id "+user.getEmail()+" is already registered with us. Please try to LogIn");
        }
        String upperCase = "(.*[A-Z].*)";
        String lowerCase = "(.*[a-z].*)";
        String numbers = "(.*[0-9].*)";
        String splChars = "(.*[@,#,$,%,&].*$)";
        String pswrd = user.getPassword();
        if (!pswrd.matches(upperCase) || !pswrd.matches(lowerCase) || !pswrd.matches(numbers) || !pswrd.matches(splChars) || pswrd.length()<8 || pswrd.contains(" ")){
            throw new PasswordInValidException("Password doesn't meet the requirements. Please try different one");
        }
        User user1;
        user1 = passwordEncryptDecryptService.passwordEncryption(user);

        userRepository.save(user1);
    }

    @Override
    public User userLogin(Login login) {
        User user = userRepository.findByUserName(login.getUserName());
        if(Objects.isNull(user)) {
            throw new UserNotFoundException("Username is incorrect. please provide the valid user name or Please sign-up and try to login");
        }
        String password=passwordEncryptDecryptService.passwordDecryption(user.getPassword());
        if(!password.equals(login.getPassword())) {
            throw new PasswordIncorrectException("Password is incorrect. Please give correct password or reset your password by clicking forgot password");
        }
        return user;
    }

    @Override
    public User forgotUserName(ForgotRequest forgotRequest) {
        User user = userRepository.findByEmail(forgotRequest.getEmail());
        if(Objects.isNull(user)) {
            throw new UserNotFoundException("Email Id "+forgotRequest.getEmail()+" not found, Please provide the valid email");
        }
        return user;
    }

    @Override
    public User forgotPassword(ForgotPasswordRequest forgotRequest) {
        Optional<User> reg = Optional.ofNullable(userRepository.findByEmail(forgotRequest.getEmail()));
        if (!reg.isPresent()){
            throw new UserNotFoundException("Email Id "+forgotRequest.getEmail()+" not found, Please provide the valid email");
        }
        User user = userRepository.findByUserName(forgotRequest.getUserName());
        if (Objects.isNull(user)){
            throw new PasswordInValidException("User Name "+forgotRequest.getUserName()+" not found, Please provide the valid user name.");
        }
        return user;
    }
}
