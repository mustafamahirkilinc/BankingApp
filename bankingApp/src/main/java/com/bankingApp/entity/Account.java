package com.bankingApp.entity;

import java.math.BigDecimal;
import java.util.*;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Account {

	@Id
	@GeneratedValue
	private Long id;
	
	private String accountNo;
	
	private BigDecimal balance;
	
	@ManyToMany(mappedBy="accounts")
	private List<Customer> customers = new ArrayList<>();

}
