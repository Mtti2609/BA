package com.API.API.service;

import com.API.API.model.Payment;
import com.API.API.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(Integer id) {
        return paymentRepository.findById(id);
    }

    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    public Payment updatePaymentStatus(Integer id, String paymentStatus) {
        return paymentRepository.findById(id)
                .map(payment -> {
                    try {
                        Payment.PaymentStatus status = Payment.PaymentStatus.valueOf(paymentStatus);
                        payment.setPaymentStatus(status);
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException("Invalid payment status: " + paymentStatus);
                    }
                    return paymentRepository.save(payment);
                })
                .orElseThrow(() -> new RuntimeException("Payment not found with ID: " + id));
    }


    public void deletePayment(Integer id) {
        paymentRepository.deleteById(id);
    }
}
