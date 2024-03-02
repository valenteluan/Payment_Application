package com.paymentapp.services;

import com.paymentapp.domain.transaction.Transaction;
import com.paymentapp.domain.user.User;
import com.paymentapp.dtos.TransactionDTO;
import com.paymentapp.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionsRepository;

    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {
        User payer = userService.findUserById(transactionDTO.payer_id());
        User payee = userService.findUserById(transactionDTO.payee_id());

        System.out.println(payer);
        System.out.println(payee);

        userService.validTransfer(payer, transactionDTO.amount());

        Transaction newtransaction = new Transaction();
        newtransaction.setAmount(transactionDTO.amount());
        newtransaction.setPayer(payer);
        newtransaction.setPayee(payee);
        newtransaction.setTimestemp(LocalDateTime.now());

        payer.setAmount(payer.getAmount().subtract(transactionDTO.amount()));
        payee.setAmount(payee.getAmount().add(transactionDTO.amount()));

        transactionsRepository.save(newtransaction);
        userService.saveUser(payer);
        userService.saveUser(payee);

        return newtransaction;
    }
}
