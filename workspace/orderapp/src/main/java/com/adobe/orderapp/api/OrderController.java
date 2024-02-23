package com.adobe.orderapp.api;

import java.util.List;

import com.adobe.orderapp.entity.Order;
import com.adobe.orderapp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api/orders")
public class OrderController {

	@Autowired
	private OrderService service;
	
	@GetMapping()
	public List<Order> getOrders() {
		return service.getOrders();
	}
	
	@PostMapping()
	public String placeOrder(@RequestBody Order order) {
		service.placeOrder(order);
		return "Order is placed!!!";
	}
}
