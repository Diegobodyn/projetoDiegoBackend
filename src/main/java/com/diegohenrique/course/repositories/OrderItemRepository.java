package com.diegohenrique.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diegohenrique.course.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
