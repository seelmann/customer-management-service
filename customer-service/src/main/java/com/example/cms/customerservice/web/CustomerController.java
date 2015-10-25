package com.example.cms.customerservice.web;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.cms.customerservice.domain.Customer;
import com.example.cms.customerservice.service.CustomerService;

@RestController
@RequestMapping(value = "/customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Iterable<Customer>> getAllCustomers() {
        Iterable<Customer> customers = service.getAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        Customer customer = service.get(id);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer, UriComponentsBuilder ucb) {
        Customer createdCustomer = service.create(customer);

        URI uri = ucb.path("/customers/").path(createdCustomer.getId().toString()).build().toUri();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri);

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @Valid @RequestBody Customer customer) {
        service.update(id, customer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
