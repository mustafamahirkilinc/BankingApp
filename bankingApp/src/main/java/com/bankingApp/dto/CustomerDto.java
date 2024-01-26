package com.bankingApp.dto;

import lombok.Data;

@Data
public class CustomerDto {

    private Long id;

    private String firstName;

    private String lastName;

    private String emailAddress;

    private String phoneNumber;
    
}
