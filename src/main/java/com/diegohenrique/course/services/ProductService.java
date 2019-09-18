package com.diegohenrique.course.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.diegohenrique.course.entities.Product;
import com.diegohenrique.course.repositories.ProductRepository;
import com.diegohenrique.course.services.exceptions.DatabaseException;
import com.diegohenrique.course.services.exceptions.ResourceNotFoundException;


@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	public List<Product> findAll() {
		return repository.findAll();
	}
	
	public Product findById(Long id) {
		Optional<Product> obj = repository.findById(id);
		return obj.get();
	}
	
	// Diego alone!  ---------------------
		public Product insert(Product obj) {
			return repository.save(obj);
			
		}
		
		public void delete(Long id) {
			try {
				repository.deleteById(id);
			}catch (EmptyResultDataAccessException e) {
				throw new ResourceNotFoundException(id);
			}catch (DataIntegrityViolationException e) {
				throw new DatabaseException(e.getMessage());
			}
		} 
		
		public Product update(Long id, Product obj) {
			try { 
				Product entity = repository.getOne(id);
			updateData(entity, obj);
			return repository.save(entity);
			} catch ( EntityNotFoundException e) {
			 throw new ResourceNotFoundException(id);
			}
		}

		private void updateData(Product entity, Product obj) {
			entity.setName(obj.getName());

		} 
	
}
