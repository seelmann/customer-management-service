package com.example.cms.customerservice.service;

import org.springframework.data.repository.CrudRepository;

import com.example.cms.customerservice.domain.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

}
