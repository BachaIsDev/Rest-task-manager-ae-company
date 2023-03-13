package com.example.demotask.service;

import com.example.demotask.model.MyUser;
import com.example.demotask.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public MyUser getUserByUsername(String username){
        Optional<MyUser> user = userRepo.findByUsername(username);
        return user.orElse(null);
    }

}
