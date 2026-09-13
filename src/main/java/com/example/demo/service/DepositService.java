package com.example.demo.service;
import com.example.demo.model.Account;
import com.example.demo.model.DepositTransaction;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.DepositRepository;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

@Service
public class DepositService {

    private final AccountRepository accountRepository;
    private final DepositRepository depositRepository;
        public DepositService(
            AccountRepository accountRepository,
            DepositRepository depositTransactionRepository) {
        this.accountRepository = accountRepository;
        this.depositRepository = depositTransactionRepository;
    }
@Transactional 
      public void deposit(Long accountId, Double amount) {

        // findById
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found" + accountId));

        if (amount == null || amount <= 0) {
        throw new RuntimeException("Invalid deposit amount");
}

        //เพิ่มจำนวนเงินเข้า balance แล้วบันทึก Account
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);

        // สร้าง DepositTransaction ผูกกับ Account  แล้วบันทึกลง Database 
        DepositTransaction transaction = new DepositTransaction();
        transaction.setAmount(amount);
        transaction.setAccount(account);
        depositRepository.save(transaction);
        throw new RuntimeException("Test Rollback");
    }
}