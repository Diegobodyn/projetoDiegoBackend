package com.diegohenrique.course.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diegohenrique.course.entities.Order;
import com.diegohenrique.course.entities.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findByClient(User client);
}
