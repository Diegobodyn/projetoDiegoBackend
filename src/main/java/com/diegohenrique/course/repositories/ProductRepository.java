package com.diegohenrique.course.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.diegohenrique.course.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {


}
