package com.example.microservices.order_service1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.microservices.order_service1.model.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>{

}
