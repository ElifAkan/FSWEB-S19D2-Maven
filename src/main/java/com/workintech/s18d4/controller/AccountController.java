package com.workintech.s18d4.controller;

import com.workintech.s18d4.dto.AccountResponse;
import com.workintech.s18d4.dto.CustomerResponse;
import com.workintech.s18d4.entity.Account;
import com.workintech.s18d4.entity.Customer;
import com.workintech.s18d4.service.AccountService;
import com.workintech.s18d4.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/account")

public class AccountController {
    private AccountService accountService;
    private CustomerService customerService;

    @GetMapping
    public List<Account>findAll(){
        return accountService.findAll();
    }

    @PostMapping("/{customerId}")
    public AccountResponse save(@PathVariable("customerId")long customerId,@RequestBody Account account){
        Customer customer = customerService.find(customerId);
        if(customer != null){
            customer.getAccount().add(account);
            account.setCustomer(customer);
            accountService.save(account);
        }
        else{
            throw new RuntimeException("no customer found!");
        }
        return new AccountResponse(account.getId(),account.getAccountName(), account.getMoneyAmount(),new CustomerResponse(customer.getId(),customer.getEmail(),customer.getSalary));
    }
}
