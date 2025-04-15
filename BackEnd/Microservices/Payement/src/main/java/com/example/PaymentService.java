package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    // Create
    public Payment createPayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    // Read
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    // Update
    public Payment updatePayment(Long id, Payment paymentDetails) {
        Payment payment = getPaymentById(id);

        // Handle case when payment is not found
        if (payment == null) {
            throw new ResourceNotFoundException("Payment not found for ID: " + id); // Or handle this as per your exception strategy
        }

        payment.setMontant(paymentDetails.getMontant());
        payment.setDatePaiement(paymentDetails.getDatePaiement());
        payment.setStatus(paymentDetails.getStatus());

        // Save the updated payment
        return paymentRepository.save(payment);
    }

    // Delete
    public void deletePayment(Long id) {
        Payment payment = getPaymentById(id);
        paymentRepository.delete(payment);
    }
}
