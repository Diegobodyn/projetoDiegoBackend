package com.diegohenrique.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diegohenrique.course.entities.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
