package com.diegohenrique.course.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diegohenrique.course.dto.ProductDTO;
import com.diegohenrique.course.entities.Product;
import com.diegohenrique.course.repositories.ProductRepository;
import com.diegohenrique.course.services.exceptions.DatabaseException;
import com.diegohenrique.course.services.exceptions.ResourceNotFoundException;


@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	public List<ProductDTO> findAll() {
		List<Product> list = repository.findAll();
		return list.stream().map(e -> new ProductDTO(e)).collect(Collectors.toList());
	}
	
	public ProductDTO findById (Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
		return new ProductDTO(entity);
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
		
		@Transactional
		public ProductDTO update(Long id, ProductDTO dto) {
			try { 
			Product entity = repository.getOne(id);
			updateData(entity, dto);
			entity = repository.save(entity);
			return new ProductDTO(entity); 
			} catch ( EntityNotFoundException e) {
			 throw new ResourceNotFoundException(id);
			}
		}

		private void updateData(Product entity, ProductDTO dto) {
			entity.setId(dto.getId());
			entity.setName(dto.getName());
			entity.setDescription(dto.getDescription());
			entity.setPrice(dto.getPrice());
			entity.setImgUrl(dto.getImgUrl());
			
			
		
		} 
		
	
}
