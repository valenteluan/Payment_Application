package com.paymentapp.services;

import com.paymentapp.domain.transaction.Transaction;
import com.paymentapp.domain.user.User;
import com.paymentapp.dtos.TransactionDTO;
import com.paymentapp.repositories.TransactionsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionsRepository transactionsRepository;

    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {
        User payer = userService.findUserById(transactionDTO.payerId());
        User payee = userService.findUserById(transactionDTO.payeeId());

        userService.validTransfer(payer, transactionDTO.value());

        Transaction newtransaction = new Transaction();
        newtransaction.setValue(transactionDTO.value());
        newtransaction.setPayer(payer);
        newtransaction.setPayee(payee);
        newtransaction.setTimestemp(LocalDateTime.now());

        payer.setAmount(payer.getAmount().subtract(transactionDTO.value()));
        payee.setAmount(payee.getAmount().add(transactionDTO.value()));

        transactionsRepository.save(newtransaction);
        userService.saveUser(payer);
        userService.saveUser(payee);

        return newtransaction;
    }
}
