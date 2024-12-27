package com.API.API.controller;

import com.API.API.model.Payment;
import com.API.API.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Integer id) {
        Optional<Payment> payment = paymentService.getPaymentById(id);
        return payment.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        try {
            Payment savedPayment = paymentService.createPayment(payment);
            return ResponseEntity.ok(savedPayment);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Payment> updatePaymentStatus(@PathVariable Integer id, @RequestBody Map<String, String> updateFields) {
        try {
            if (!updateFields.containsKey("paymentStatus")) {
                return ResponseEntity.badRequest().build();
            }
            String paymentStatus = updateFields.get("paymentStatus");
            Payment updatedPayment = paymentService.updatePaymentStatus(id, paymentStatus);
            return ResponseEntity.ok(updatedPayment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Integer id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}
