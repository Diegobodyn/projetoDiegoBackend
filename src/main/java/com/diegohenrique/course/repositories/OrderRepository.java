package com.diegohenrique.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diegohenrique.course.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
