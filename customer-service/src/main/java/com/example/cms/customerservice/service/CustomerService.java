package com.example.cms.customerservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cms.customerservice.domain.Customer;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public Customer get(Long id) {
        return repository.findOne(id);
    }

}
