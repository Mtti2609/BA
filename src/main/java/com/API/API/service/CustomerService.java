package com.API.API.service;

import com.API.API.model.Customer;
import com.API.API.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    // Lấy danh sách tất cả khách hàng
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Lấy khách hàng theo ID
    public Optional<Customer> getCustomerById(Integer id) {
        return customerRepository.findById(id);
    }

    // Tạo mới khách hàng
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    // Cập nhật thông tin khách hàng
    public Customer updateCustomer(Integer id, Customer updatedCustomer) {
        return customerRepository.findById(id)
                .map(customer -> {
                    customer.setName(updatedCustomer.getName());
                    customer.setEmail(updatedCustomer.getEmail());
                    customer.setPhone(updatedCustomer.getPhone());
                    customer.setAddress(updatedCustomer.getAddress());
                    customer.setDateOfBirth(updatedCustomer.getDateOfBirth());
                    customer.setGender(updatedCustomer.getGender());
                    customer.setClassification(updatedCustomer.getClassification());
                    return customerRepository.save(customer);
                })
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }

    // Xóa khách hàng
    public void deleteCustomer(Integer id) {
        customerRepository.deleteById(id);
    }
}
