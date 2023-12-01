package com.siin.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.siin.customer.models.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Long>{
    
}
