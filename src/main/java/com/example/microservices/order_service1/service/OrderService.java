package com.example.microservices.order_service1.service;

import java.util.UUID;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.microservices.order_service1.client.InventoryClient;
//import com.example.microservices.order_service1.client.InventoryClient;
import com.example.microservices.order_service1.dto.OrderRequest;
import com.example.microservices.order_service1.event.OrderPlacedEvent;
import com.example.microservices.order_service1.model.Order;
import com.example.microservices.order_service1.repository.OrderRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

	private final OrderRepository orderRepository;

	private final InventoryClient inventoryClient;

	private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
	
	// map orderRequest to Order object and save order obj to order repository
	public void placeOrder(OrderRequest orderRequest) {

		boolean inStock = inventoryClient.isInStock(orderRequest.getSkuCode(), orderRequest.getQuantity());
		if (inStock) {
			Order order = new Order();
			order.setOrderNumber(UUID.randomUUID().toString());
			order.setPrice(orderRequest.getPrice());
			order.setSkuCode(orderRequest.getSkuCode());
			order.setQuantity(orderRequest.getQuantity());
			orderRepository.save(order);
			
			// send the message to kafka topic
			// ordernumber and email are the 2 fields which needs to be send to kafka topic
			OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent();
			orderPlacedEvent.setOrderNumber(order.getOrderNumber());
			log.info("Start -  Sending OrderPlacedEvent {} to kafka topic order-placed", orderPlacedEvent);
			kafkaTemplate.send("order-placed", orderPlacedEvent);
			log.info("End -  Sending OrderPlacedEvent {} to kafka topic order-placed", orderPlacedEvent);
			log.info("order placed successfully");
		} else {
			throw new RuntimeException("Product with skuCode " + orderRequest.getSkuCode() + " is not in stock");
		}
	}

}
