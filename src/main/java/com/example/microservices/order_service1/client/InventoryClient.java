package com.example.microservices.order_service1.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

//@FeignClient(value = "inventory", url = "${inventory.url}")
public interface InventoryClient {
	
//	@RequestMapping(method = RequestMethod.GET, value = "/api/inventory")
	@GetExchange("/api/inventory")
	@CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
	@Retry(name = "inventory")
	boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);
	
	default boolean fallbackMethod(String skuCode, Integer quantity, Throwable throwable) {
//		log.info("Cannot get inventory for skuCode {}, failure reason: {}", skuCode, throwable.getMessage());
		return false;
	}
}
