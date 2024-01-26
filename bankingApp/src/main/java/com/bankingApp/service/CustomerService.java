package com.bankingApp.service;

import java.util.*;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.bankingApp.dto.AccountDto;
import com.bankingApp.dto.CustomerDto;
import com.bankingApp.entity.Customer;
import com.bankingApp.repository.IAccountRepository;
import com.bankingApp.repository.ICustomerRepository;

@Service
public class CustomerService {

    private final ICustomerRepository customerRepository;
    private final IAccountRepository accountRepository;
    private final ModelMapper modelMapper;

    public CustomerService(ICustomerRepository customerRepository, IAccountRepository accountRepository, ModelMapper modelMapper) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
    }

    public CustomerDto addCustomer(CustomerDto customerDto) {
        Customer customer = modelMapper.map(customerDto, Customer.class);
        customerRepository.save(customer);
        return modelMapper.map(customer, CustomerDto.class);
    }
    
    public CustomerDto updateCustomer(Long customerId, CustomerDto customerDto) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer existingCustomer = optionalCustomer.get();
            existingCustomer.setFirstName(customerDto.getFirstName());
            existingCustomer.setLastName(customerDto.getLastName());
            existingCustomer.setEmailAddress(customerDto.getEmailAddress());
            existingCustomer.setPhoneNumber(customerDto.getPhoneNumber());
            customerRepository.save(existingCustomer);
            return modelMapper.map(existingCustomer, CustomerDto.class);
        }
        return null; 
    }
    
    public CustomerDto deleteCustomer(Long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer deletedCustomer = optionalCustomer.get();
            customerRepository.delete(deletedCustomer);
            return modelMapper.map(deletedCustomer, CustomerDto.class);
        }
        return null; 
    }
    
    public CustomerDto getCustomerById(Long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        return optionalCustomer.map(customer -> modelMapper.map(customer, CustomerDto.class)).orElse(null);
    }
    
    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream()
                .map(customer -> modelMapper.map(customer, CustomerDto.class))
                .collect(Collectors.toList());
    }

    public List<AccountDto> getAccountsByCustomerId(Long customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            return customer.getAccounts().stream()
                    .map(account -> modelMapper.map(account, AccountDto.class))
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
    
}
