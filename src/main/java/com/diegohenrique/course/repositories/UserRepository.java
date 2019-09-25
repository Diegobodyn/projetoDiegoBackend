package com.diegohenrique.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diegohenrique.course.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByEmail(String email);

}
