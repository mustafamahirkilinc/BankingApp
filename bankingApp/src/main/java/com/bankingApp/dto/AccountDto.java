package com.bankingApp.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class AccountDto {

    private Long id;

    private String accountNo;

    private BigDecimal balance;
}
