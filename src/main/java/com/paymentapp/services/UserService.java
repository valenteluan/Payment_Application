package com.paymentapp.services;

import com.paymentapp.domain.user.User;
import com.paymentapp.domain.user.UserType;
import com.paymentapp.dtos.UserDTO;
import com.paymentapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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

    public void saveUser(User user) {
        userRepository.save(user);
    }

    public User findUserById(Long id) throws Exception {
        return userRepository.findUserById(id).orElseThrow(() -> new Exception("Usuário não encontrado!"));
    }

    public void validTransfer(User payer, User payee, BigDecimal value) throws Exception {
        if (payer.getUserType() == UserType.SELLER) {
            throw new Exception("Usuário lojista não está autorizado a realizar transações!");
        }

        if (payer.getAmount().compareTo(value) < 0) {
            throw new Exception("Saldo insuficiente para realizar essa transferência!");
        }

        if (Objects.equals(payer.getId(), payee.getId())) {
            throw new Exception("Remetente e Destinatário não podem ser iguais!");
        }

    }

}
