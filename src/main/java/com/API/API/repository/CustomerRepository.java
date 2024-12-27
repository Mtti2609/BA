package com.API.API.repository;

import com.API.API.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    List<Customer> findByCustomerIdNotIn(List<Integer> customerIds);
}
