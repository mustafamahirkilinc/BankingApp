package com.bankingApp.controller;

import java.math.BigDecimal;
import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bankingApp.dto.AccountDto;
import com.bankingApp.dto.CustomerDto;
import com.bankingApp.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

	private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create/{customerId}")
    public ResponseEntity<Object> createAccount(@RequestBody AccountDto accountDto, @PathVariable Long customerId) {
        try {
            AccountDto createdAccount = accountService.createAccount(accountDto, customerId);
            return new ResponseEntity<>(createdAccount.getAccountNo() + " No'lu hesap oluşturuldu. Hesap id:" + 
            		createdAccount.getId() , HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/deposit/{accountId}")
    public ResponseEntity<Object> depositToAccount(@PathVariable Long accountId, @RequestParam BigDecimal amount) {
        try {
            accountService.depositToAccount(accountId, amount);
            return new ResponseEntity<>("Para başarıyla hesaba yatırıldı.", HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/withdraw/{accountId}")
    public ResponseEntity<Object> withdrawFromAccount(@PathVariable Long accountId, @RequestParam BigDecimal amount) {
        try {
            accountService.withdrawFromAccount(accountId, amount);
            return new ResponseEntity<>("Para başarıyla hesaptan çekildi.", HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    @GetMapping("/balance/{accountId}")
    public ResponseEntity<Object> getAccountBalance(@PathVariable Long accountId) {
        try {
            BigDecimal balance = accountService.getAccountBalance(accountId);
            if (balance != null) {
                return new ResponseEntity<>("Hesap bakiyesi: " + balance, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Hesap bulunamadı.", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/customers/{accountId}")
    public ResponseEntity<Object> getAccountCustomers(@PathVariable Long accountId) {
        try {
            List<CustomerDto> customers = accountService.getAccountCustomers(accountId);
            return new ResponseEntity<>(customers, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
     
}
