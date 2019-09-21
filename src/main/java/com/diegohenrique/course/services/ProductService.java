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

import com.diegohenrique.course.dto.CategoryDTO;
import com.diegohenrique.course.dto.ProductCategoriesDTO;
import com.diegohenrique.course.dto.ProductDTO;
import com.diegohenrique.course.entities.Category;
import com.diegohenrique.course.entities.Product;
import com.diegohenrique.course.repositories.CategoryRepository;
import com.diegohenrique.course.repositories.ProductRepository;
import com.diegohenrique.course.services.exceptions.DatabaseException;
import com.diegohenrique.course.services.exceptions.ResourceNotFoundException;


@Service
public class ProductService {

	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	
	
	public List<ProductDTO> findAll() {
		List<Product> list = repository.findAll();
		return list.stream().map(e -> new ProductDTO(e)).collect(Collectors.toList());
	}
	
	public ProductDTO findById (Long id) {
		Optional<Product> obj = repository.findById(id);
		Product entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
		return new ProductDTO(entity);
	}
	
	
	@Transactional
	public ProductDTO insert(ProductCategoriesDTO dto) {
		Product entity = dto.toEntity();
		setProductCategories(entity, dto.getCategories() );
		entity= repository.save(entity);
		return new ProductDTO(entity);
		
	}
		
		private void setProductCategories(Product entity, List<CategoryDTO> categories) {
		entity.getCategories().clear();
		for (CategoryDTO dto: categories ){
			Category category = categoryRepository.getOne(dto.getId());
			entity.getCategories().add(category);
		}
		
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
