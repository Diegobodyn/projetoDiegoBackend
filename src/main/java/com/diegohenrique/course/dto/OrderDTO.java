package com.diegohenrique.course.dto;

import java.io.Serializable;
import java.time.Instant;

import com.diegohenrique.course.entities.Order;
import com.diegohenrique.course.entities.User;
import com.diegohenrique.course.entities.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

public class OrderDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone= "GMT")
	private Instant moment;
	private OrderStatus orderStatus;
	private long clientId;
	private String clienteName;
	private String clienteEmail;
	
	
	public OrderDTO() {
		
	}


	public OrderDTO(Long id, Instant moment, OrderStatus orderStatus, long clientId, String clienteName,
			String clienteEmail) {
		this.id = id;
		this.moment = moment;
		this.orderStatus = orderStatus;
		this.clientId = clientId;
		this.clienteName = clienteName;
		this.clienteEmail = clienteEmail;
	}
	
	public OrderDTO(Order entity) {
		if(entity.getClient() ==null ) {
			throw new IllegalArgumentException("Error instantiating OrderDTO: client was null");	
		}
		this.id = entity.getId() ;
		this.moment = entity.getMoment();
		this.orderStatus = entity.getOrderStatus();
		this.clientId = entity.getClient().getId();
		this.clienteName = entity.getClient().getName();
		this.clienteEmail = entity.getClient().getEmail();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Instant getMoment() {
		return moment;
	}


	public void setMoment(Instant moment) {
		this.moment = moment;
	}


	public OrderStatus getOrderStatus() {
		return orderStatus;
	}


	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}


	public long getClientId() {
		return clientId;
	}


	public void setClientId(long clientId) {
		this.clientId = clientId;
	}


	public String getClienteName() {
		return clienteName;
	}


	public void setClienteName(String clienteName) {
		this.clienteName = clienteName;
	}


	public String getClienteEmail() {
		return clienteEmail;
	}


	public void setClienteEmail(String clienteEmail) {
		this.clienteEmail = clienteEmail;
	}
	
	public Order toEntity() {
		User client = new User(clientId, clienteName, clienteEmail, null, null);
		return new Order(id, moment, orderStatus ,client);
	}
	
}
