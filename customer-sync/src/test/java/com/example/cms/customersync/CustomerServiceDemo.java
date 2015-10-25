package com.example.cms.customersync;

import java.math.BigInteger;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.predic8.common._1.AddressType;
import com.predic8.common._1.PersonType;
import com.predic8.crm._1.CompanyAddressType;
import com.predic8.crm._1.CustomerType;
import com.predic8.wsdl.crm.crmservice._1.CRMServicePT;
import com.predic8.wsdl.crm.crmservice._1.CustomerService;

public class CustomerServiceDemo {

    private CRMServicePT port;

    @Before
    public void setup() {
        CustomerService customerService = new CustomerService();
        port = customerService.getCRMServicePTPort();

    }

    @Ignore
    @Test
    public void testGetOne() throws Exception {
        CustomerType customer = port.get("aaa-1000000");
        print(customer);
    }

    @Ignore
    @Test
    public void testGetAll() {
        List<CustomerType> all = port.getAll();
        System.out.println(all.size());
        // for (CustomerType customer : all) {
        // print(customer);
        // }
    }

    @Ignore
    @Test
    public void testCreateOne() throws Exception {
        CustomerType c1 = newCustomer();
        String id = "aaa-1000000";
        c1.setId(id);
        port.create(c1);
    }

    @Ignore
    @Test
    public void testCreateMany() throws Exception {
        for (int i = 1000000; i <= 1001000; i++) {
            CustomerType c1 = newCustomer();
            String id = "aaa-" + i;
            c1.setId(id);
            port.create(c1);
        }
    }

    private CustomerType newCustomer() {
        CustomerType customer = new CustomerType();
        CompanyAddressType companyAddress = new CompanyAddressType();
        PersonType person = new PersonType();
        person.setFirstName("John");
        person.setLastName("Smith");
        person.setAge(BigInteger.valueOf(42l));
        AddressType privateAddress = new AddressType();
        privateAddress.setCity("München");
        privateAddress.setStreet("Marienplatz 1");
        privateAddress.setZipCode("80331");
        privateAddress.setCountry("DE");
        person.setAddress(privateAddress);
        customer.setPerson(person);
        companyAddress.setCity("London");
        companyAddress.setStreet("10 Downing Street");
        companyAddress.setZipCode("SW1A 2AA");
        companyAddress.setCountry("UK");
        customer.setAddress(companyAddress);
        return customer;
    }

    private void print(CustomerType customer) throws Exception {
        String json = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(customer);
        System.out.println(json);
    }

}
