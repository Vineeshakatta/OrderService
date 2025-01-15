package com.example.microservices.order_service1.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {

	private Long id;
	private String orderNumber;
	private String skuCode;
	private BigDecimal price;
	private Integer quantity;
//	private UserDetails userDetails;
	
//	@Data
//	@AllArgsConstructor
//	@NoArgsConstructor
//	public class UserDetails {
//		private String email;
//		private String firstName;
//		private String lastName;
//	}
}
