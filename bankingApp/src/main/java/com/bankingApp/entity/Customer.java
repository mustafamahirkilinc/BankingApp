package com.bankingApp.entity;

import java.util.*;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Customer {

	@Id
	@GeneratedValue
	private Long id;
	
	private String firstName;
	private String lastName;
	private String emailAddress;
	private String phoneNumber;
	
	@ManyToMany
	@JoinTable(name = "customer_account",
	           joinColumns = @JoinColumn(name = "customer_id"),
	           inverseJoinColumns = @JoinColumn(name = "account_id"))
	private List<Account> accounts = new ArrayList<>();
	
}
