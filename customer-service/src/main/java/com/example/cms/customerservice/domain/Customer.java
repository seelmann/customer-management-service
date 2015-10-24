package com.example.cms.customerservice.domain;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    private String firstname;
    private String lastname;
    private Integer age;
    private String emailAddress;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "city", column = @Column(name = "priv_city") ),
            @AttributeOverride(name = "street", column = @Column(name = "priv_street") ),
            @AttributeOverride(name = "zipCode", column = @Column(name = "priv_zipcode") ),
            @AttributeOverride(name = "country", column = @Column(name = "priv_country") ) })
    private Address privateAddress;

    @Embedded
    @AttributeOverrides({ @AttributeOverride(name = "city", column = @Column(name = "company_city") ),
            @AttributeOverride(name = "street", column = @Column(name = "company_street") ),
            @AttributeOverride(name = "zipCode", column = @Column(name = "company_zipcode") ),
            @AttributeOverride(name = "country", column = @Column(name = "company_country") ) })
    private Address companyAddress;

    Customer() {
    }

    public Customer(String firstname, String lastname, Integer age, String emailAddress, Address privateAddress,
            Address companyAddress) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.emailAddress = emailAddress;
        this.privateAddress = privateAddress;
        this.companyAddress = companyAddress;
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Integer getAge() {
        return age;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Address getPrivateAddress() {
        return privateAddress;
    }

    public Address getCompanyAddress() {
        return companyAddress;
    }

}
