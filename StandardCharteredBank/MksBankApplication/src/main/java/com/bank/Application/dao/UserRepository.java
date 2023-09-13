package com.bank.Application.dao;

import com.bank.Application.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {


    Optional<User> findByPassword(String password);

    User findByUserName(String userName);

    User findByEmail(String email);
}
