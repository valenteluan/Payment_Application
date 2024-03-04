package com.paymentapp.services;

import com.paymentapp.domain.transaction.Transaction;
import com.paymentapp.domain.user.User;
import com.paymentapp.dtos.TransactionDTO;
import com.paymentapp.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository transactionsRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Transaction createTransaction(TransactionDTO transactionDTO) throws Exception {
        User payer = userService.findUserById(transactionDTO.payer_id());
        User payee = userService.findUserById(transactionDTO.payee_id());

        userService.validTransfer(payer, transactionDTO.amount());

        boolean isAuthorized = this.authorizeTransaction(payer, transactionDTO.amount());
        if (!isAuthorized) {
            throw new Exception("Transação não Autorizada!");
        }

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

    public boolean authorizeTransaction(User payer, BigDecimal amount) {
        ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/5794d450-d2e2-4412-8131-73d0293ac1cc", Map.class);

        if (authorizationResponse.getStatusCode() == HttpStatus.OK) {
            String message = (String) authorizationResponse.getBody().get("message");
            return "Autorizado".equalsIgnoreCase(message);
        } else {
            return false;
        }

    }


}
