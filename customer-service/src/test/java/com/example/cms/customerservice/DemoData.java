package com.example.cms.customerservice;

import com.example.cms.customerservice.domain.Address;
import com.example.cms.customerservice.domain.Customer;

public class DemoData {

    public static final Address A1 = new Address("Marienplatz 1", "M\u00fcnchen", "80331", "DE");
    public static final Address A2 = new Address("10 Downing Street", "London", "SW1A 2AA", "UK");
    public static final Address A3 = new Address("11 Wall St", "New York", "NY 10005", "US");

    public static final Customer C1 = new Customer("John", "Smith", 42, "john.smith@example.com", A1, A2);
    public static final Customer C2 = new Customer("\u0422\u0430\u0442\u044c\u044f\u043d\u0430",
            "\u0415\u0436\u043e\u0301\u0432", 23, "12345@example.com", A2, A3);
}
