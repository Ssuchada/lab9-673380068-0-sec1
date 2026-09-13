package com.example.demo.controller;

import com.example.demo.model.Account;
import com.example.demo.service.AccountService;
import com.example.demo.service.DepositService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    private final AccountService accountService;
    private final DepositService depositService;

    public AccountController(
            AccountService accountService,
            DepositService depositService) {
        this.accountService = accountService;
        this.depositService = depositService;
    }

    // POST /accounts
    @PostMapping
    public ResponseEntity<Account> createAccount(
            @RequestBody Account account) {

        Account createdAccount = accountService.createAccount(account);

        return ResponseEntity.ok(createdAccount);
    }

    // GET /accounts/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Account> getAccount(
            @PathVariable Long id) {

        Account account = accountService.getAccountById(id);

        return ResponseEntity.ok(account);
    }

    // POST /accounts/{id}/deposit
    @PostMapping("/{id}/deposit")
    public ResponseEntity<String> deposit(
            @PathVariable Long id,
            @RequestBody Map<String, Double> request) {

        Double amount = request.get("amount");

        depositService.deposit(id, amount);

        return ResponseEntity.ok("Deposit successful");
    }
}
