package com.diegohenrique.course.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.diegohenrique.course.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {


}
