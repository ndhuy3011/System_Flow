package com.siin.customer.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.siin.customer.models.Customer;
import com.siin.customer.repository.CustomerRepository;
import com.siin.customer.service.CustomerService;

import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    @Resource
    CustomerRepository customerRepository;

    @Transactional
    public Customer createCustomer(Customer input) {
        log.info("CustomerServiceImpl.createCustomer");

        Assert.notNull(input.getUuid(), "UUID not null");
        Assert.notNull(input.getNumberPhone(), "Number phone not null");

        return customerRepository.save(input);
    }

    @Transactional
    public Customer modifiedCustom(Customer input) {
        Assert.isTrue(!customerRepository.existsById(input.getCustNo()), "Customer not found");
        return customerRepository.save(input);
    }

}
