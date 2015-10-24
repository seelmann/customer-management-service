package com.example.cms.customerservice;

import com.example.cms.customerservice.domain.Address;
import com.example.cms.customerservice.domain.Customer;

public class DemoData {

    public static final Address a1() {
        return new Address("Marienplatz 1", "M\u00fcnchen", "80331", "DE");
    }

    public static final Address a2() {
        return new Address("10 Downing Street", "London", "SW1A 2AA", "UK");
    }

    public static final Address a3() {
        return new Address("11 Wall St", "New York", "NY 10005", "US");
    }

    public static final Customer c1() {
        return new Customer("John", "Smith", 42, "john.smith@example.com", a1(), a2());
    }

    public static final Customer c2() {
        return new Customer("\u0422\u0430\u0442\u044c\u044f\u043d\u0430", "\u0415\u0436\u043e\u0301\u0432", 23,
                "12345@example.com", a2(), a3());
    }
}
