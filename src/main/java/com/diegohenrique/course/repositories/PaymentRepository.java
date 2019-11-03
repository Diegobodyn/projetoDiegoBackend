package com.diegohenrique.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diegohenrique.course.entities.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {


}
