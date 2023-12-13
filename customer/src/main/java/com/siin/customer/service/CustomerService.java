package com.siin.customer.service;

import com.siin.customer.models.Customer;

public interface CustomerService {
    Customer createCustomer(Customer input);

    Customer modifiedCustom(Customer input);
}
