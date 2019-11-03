package com.diegohenrique.course.services;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.diegohenrique.course.dto.OrderDTO;
import com.diegohenrique.course.dto.OrderItemDTO;
import com.diegohenrique.course.entities.Order;
import com.diegohenrique.course.entities.OrderItem;
import com.diegohenrique.course.entities.Product;
import com.diegohenrique.course.entities.User;
import com.diegohenrique.course.entities.enums.OrderStatus;
import com.diegohenrique.course.repositories.OrderItemRepository;
import com.diegohenrique.course.repositories.OrderRepository;
import com.diegohenrique.course.repositories.ProductRepository;
import com.diegohenrique.course.repositories.UserRepository;
import com.diegohenrique.course.services.exceptions.ResourceNotFoundException;


@Service
public class OrderService {

	@Autowired
	private OrderRepository repository;
	

	@Autowired
	private AuthService authService;
	
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	
	public List<OrderDTO> findAll() {
		List<Order> list = repository.findAll();
		return list.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());
	}
	
	public OrderDTO findById (Long id) {
		Optional<Order> obj = repository.findById(id);
		Order entity = obj.orElseThrow(() -> new ResourceNotFoundException(id));
		authService.validadeOwnOrderOrAdmin(entity);
		return new OrderDTO(entity);
	}
	
	public List<OrderDTO> findByClient(){
		User client = authService.authenticated();
		List<Order> list = repository.findByClient(client);
		return list.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<OrderItemDTO> findItems(Long id) {
		Order order = repository.getOne(id);
		authService.validadeOwnOrderOrAdmin(order);
		Set<OrderItem> set = order.getItems();
		return set.stream().map(e -> new OrderItemDTO(e)).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public List<OrderDTO> findByClientId(Long clientId) {
		User client = userRepository.getOne(clientId);
		List<Order> list = repository.findByClient(client);
		return list.stream().map(e -> new OrderDTO(e)).collect(Collectors.toList());
	}

	@Transactional
	public OrderDTO placeOrder(List<OrderItemDTO> dto) {
	
		User client = authService.authenticated();
		Order order = new Order(null, Instant.now(), OrderStatus.WAITING_PAYMENT, client);
		
		for(OrderItemDTO itemDTO :dto) {
			Product product = productRepository.getOne(itemDTO.getProductId());
			OrderItem item = new OrderItem(order, product, itemDTO.getQuantity(), itemDTO.getPrice());
			order.getItems().add(item);
		}
		
		repository.save(order);
		orderItemRepository.saveAll(order.getItems());
		
		return new OrderDTO(order);
	}
	
	@Transactional
	public OrderDTO update(Long id, OrderDTO dto) {
		try { 
		Order entity = repository.getOne(id);
		updateData(entity, dto);
		entity = repository.save(entity);
		return new OrderDTO(entity); 
		} catch ( EntityNotFoundException e) {
		 throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Order entity, OrderDTO dto) {
		entity.setOrderStatus(dto.getOrderStatus());
	}
	
}
