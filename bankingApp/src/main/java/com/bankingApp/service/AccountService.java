package com.bankingApp.service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.bankingApp.dto.AccountDto;
import com.bankingApp.dto.CustomerDto;
import com.bankingApp.entity.Account;
import com.bankingApp.entity.Customer;
import com.bankingApp.repository.IAccountRepository;
import com.bankingApp.repository.ICustomerRepository;

@Service
public class AccountService {

    private final IAccountRepository accountRepository;
    private final ICustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public AccountService(IAccountRepository accountRepository, ICustomerRepository customerRepository, ModelMapper modelMapper) {
        this.accountRepository = accountRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    public AccountDto createAccount(AccountDto accountDto, Long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            Account account = modelMapper.map(accountDto, Account.class);
            account.getCustomers().add(customer);
            customer.getAccounts().add(account);
            accountRepository.save(account);
            customerRepository.save(customer);
            return modelMapper.map(account, AccountDto.class);
        }
        return null;
    }

    public void depositToAccount(Long accountId, BigDecimal amount) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        optionalAccount.ifPresent(account -> {
            account.setBalance(account.getBalance().add(amount));
            accountRepository.save(account);
        });
    }

    public void withdrawFromAccount(Long accountId, BigDecimal amount) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        optionalAccount.ifPresent(account -> {
            if (account.getBalance().compareTo(amount) >= 0) {
                account.setBalance(account.getBalance().subtract(amount));
                accountRepository.save(account);
            } else {
                throw new IllegalArgumentException("Yetersiz bakiye");
            }
        });
    }

    public BigDecimal getAccountBalance(Long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        return optionalAccount.map(Account::getBalance).orElse(null);
    }

    public List<CustomerDto> getAccountCustomers(Long accountId) {
        Optional<Account> optionalAccount = accountRepository.findById(accountId);
        return optionalAccount.map(account ->
                account.getCustomers().stream()
                        .map(customer -> modelMapper.map(customer, CustomerDto.class))
                        .collect(Collectors.toList())
        ).orElse(Collections.emptyList());
    }
    
}
