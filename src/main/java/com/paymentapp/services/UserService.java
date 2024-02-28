package com.paymentapp.services;

import com.paymentapp.domain.user.User;
import com.paymentapp.dtos.UserDTO;
import com.paymentapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User createUser(UserDTO userDTO) {
        User newUser = new User(userDTO);
        return userRepository.save(newUser);
    }

    public List<User> findAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList;
    }

}
