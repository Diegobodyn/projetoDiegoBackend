package com.diegohenrique.course.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;

import com.diegohenrique.course.entities.Product;

public class ProductCategoriesDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@NotEmpty
	@Length(min =3, max= 80, message = "length must be between 3 and 80")
	private String name;
	
	private String description;
	
	@NotEmpty
	@Positive
	private Double price;
	private String imgUrl;
	
	private List<CategoryDTO> categories = new ArrayList<>();
	
	public ProductCategoriesDTO() {
		
	}

	public ProductCategoriesDTO(String name, String description, Double price, String imgUrl) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
	}
	
	public ProductCategoriesDTO(Product entity) {
		this.name = entity.getName();
		this.description = entity.getDescription();
		this.price = entity.getPrice();
		this.imgUrl = entity.getImgUrl();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public List<CategoryDTO> getCategories() {
		return categories;
	}
	
	public Product toEntity() {
		return new Product(null, name, description, price, imgUrl);
	}
	
}
