package com.bankingApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bankingApp.entity.Customer;

public interface ICustomerRepository extends JpaRepository<Customer, Long> {

}
