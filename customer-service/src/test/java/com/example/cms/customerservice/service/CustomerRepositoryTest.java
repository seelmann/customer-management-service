package com.example.cms.customerservice.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.cms.customerservice.Application;
import com.example.cms.customerservice.DemoData;
import com.example.cms.customerservice.domain.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository repository;

    @Test
    public void testRoundtrip() {
        assertThat(repository.count(), equalTo(0L));

        Customer customer = repository.save(DemoData.C1);
        assertThat(customer, notNullValue());
        assertThat(customer.getId(), notNullValue());
        assertThat(repository.count(), equalTo(1L));

        assertThat(repository.findOne(customer.getId()), notNullValue());

        repository.delete(customer.getId());
        assertThat(repository.count(), equalTo(0L));
    }

}
