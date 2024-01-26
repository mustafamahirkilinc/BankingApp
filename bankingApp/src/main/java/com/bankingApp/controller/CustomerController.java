package com.bankingApp.controller;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bankingApp.dto.AccountDto;
import com.bankingApp.dto.CustomerDto;
import com.bankingApp.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
	this.customerService = customerService;
	}
	    
	@PostMapping("/add")
	public ResponseEntity<Object> addCustomer(@RequestBody CustomerDto customerDto) {
		try {
			CustomerDto createdCustomer = customerService.addCustomer(customerDto);
	        return new ResponseEntity<>(createdCustomer, HttpStatus.OK);
	    } catch (Exception ex) {
	    	return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
	    
	@PutMapping("/update/{customerId}")
	public ResponseEntity<Object> updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDto customerDto) {
		try {
			CustomerDto updatedCustomer = customerService.updateCustomer(customerId, customerDto);
			return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
	    } catch (IllegalArgumentException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	    } catch (NoSuchElementException ex) {
	        return new ResponseEntity<>("Müşteri bulunamadı.", HttpStatus.NOT_FOUND);
	    }
	}
	    
	@DeleteMapping("/delete/{customerId}")
	public ResponseEntity<Object> deleteCustomer(@PathVariable Long customerId) {
		try {
			CustomerDto deletedCustomer = customerService.deleteCustomer(customerId);
	        if (deletedCustomer != null) {
	        	return ResponseEntity.ok("Müşteri silindi: " + deletedCustomer.getId());
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Müşteri bulunamadı");
	        }
	    } catch (IllegalArgumentException ex) {
	    	return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	    }
	}
	    
	@GetMapping("/{customerId}")
	public ResponseEntity<Object> getCustomerById(@PathVariable Long customerId) {
		try {
			CustomerDto customer = customerService.getCustomerById(customerId);
			return new ResponseEntity<>(customer, HttpStatus.OK);
	    } catch (IllegalArgumentException ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
	    } catch (NoSuchElementException ex) {
	        return new ResponseEntity<>("Müşteri bulunamadı.", HttpStatus.NOT_FOUND);
	    }
	}
	    
	@GetMapping("/all")
	public ResponseEntity<Object> getAllCustomers() {
		try {
			List<CustomerDto> customers = customerService.getAllCustomers();
			return new ResponseEntity<>(customers, HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	    
	@GetMapping("/{customerId}/accounts")
	public ResponseEntity<Object> getAccountsByCustomerId(@PathVariable Long customerId) {
		try {
			List<AccountDto> accounts = customerService.getAccountsByCustomerId(customerId);
			return new ResponseEntity<>(accounts, HttpStatus.OK);
	    } catch (Exception ex) {
	        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

}
