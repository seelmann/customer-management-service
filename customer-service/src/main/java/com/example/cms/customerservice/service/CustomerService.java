package com.example.cms.customerservice.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.cms.customerservice.domain.Customer;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public Iterable<Customer> getAll() {
        return repository.findAll();
    }

    public Customer get(Long id) {
        Customer customer = repository.findOne(id);
        if (customer == null) {
            throw new NoSuchElementException();
        }
        return customer;
    }

    public Customer create(Customer customer) {
        return repository.save(customer);
    }

    public Customer update(Long id, Customer customer) {
        if (!repository.exists(id)) {
            throw new NoSuchElementException();
        }
        customer.setId(id);
        return repository.save(customer);
    }

}
