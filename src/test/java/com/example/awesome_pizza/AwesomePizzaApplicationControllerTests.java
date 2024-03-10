package com.example.awesome_pizza;

import com.example.awesome_pizza.Controller.OrderController;
import com.example.awesome_pizza.Model.Order;
import com.example.awesome_pizza.Model.OrderStatus;
import com.example.awesome_pizza.Model.Pizza;
import com.example.awesome_pizza.Service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AwesomePizzaApplicationControllerTests {

	@Mock
	private OrderService orderService;

	@InjectMocks
	private OrderController orderController;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testCreateOrder() {
		List<Integer> pizzasId = new ArrayList<>();
		pizzasId.add(1);
		pizzasId.add(2);

		when(orderService.createOrder(pizzasId)).thenReturn("12345");
		ResponseEntity<String> response = orderController.createOrder(pizzasId);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("12345", response.getBody());
	}

	@Test
	void testGetOrder() {
		Order order = new Order();
		Pizza pizza = new Pizza(1, "Margherita");
		order.setOrderId("12345");
		order.addPizzaToOrder(pizza);
		order.setStatus(OrderStatus.PENDING);

		when(orderService.getOrder("12345")).thenReturn(order);
		ResponseEntity<?> response = orderController.getOrder("12345");

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody() instanceof Order);
	}

	@Test
	void testGetOrderNotFound() {
		Order order = new Order();
		Pizza pizza = new Pizza(1, "Margherita");
		order.setOrderId("12345");
		order.addPizzaToOrder(pizza);
		order.setStatus(OrderStatus.PENDING);

		when(orderService.getOrder("12345")).thenReturn(order);
		ResponseEntity<?> response = orderController.getOrder("00000");

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void testGetAllOrders() {
		List<Order> orders = new ArrayList<>();
		Order order = new Order();
		Pizza pizza = new Pizza(1, "Margherita");
		order.setOrderId("12345");
		order.addPizzaToOrder(pizza);
		order.setStatus(OrderStatus.PENDING);
		orders.add(order);

		when(orderService.getAllOrders()).thenReturn(orders);
		ResponseEntity<?> response = orderController.getAllOrders();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody() instanceof List);
	}

	@Test
	void testGetPendingOrders() {
		Order order1 = new Order();
		order1.setOrderId("12345");
		order1.setStatus(OrderStatus.PENDING);

		Order order2 = new Order();
		order2.setOrderId("67890");
		order2.setStatus(OrderStatus.PENDING);

		List<Order> pendingOrders = new ArrayList<>();
		pendingOrders.add(order1);
		pendingOrders.add(order2);

		when(orderService.getPendingOrders()).thenReturn(pendingOrders);
		ResponseEntity<List<Order>> response = orderController.getAllPendingOrders();

		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Order> body = response.getBody();
		assertNotNull(body);

		assertEquals(2, body.size());
		for (Order order : body) {
			assertEquals(OrderStatus.PENDING, order.getStatus());
		}
	}

	@Test
	void testGetDoneOrders() {
		Order order1 = new Order();
		order1.setOrderId("12345");
		order1.setStatus(OrderStatus.DONE);

		Order order2 = new Order();
		order2.setOrderId("67890");
		order2.setStatus(OrderStatus.DONE);

		List<Order> doneOrders = new ArrayList<>();
		doneOrders.add(order1);
		doneOrders.add(order2);

		when(orderService.getDoneOrders()).thenReturn(doneOrders);
		ResponseEntity<List<Order>> response = orderController.getAllDoneOrders();

		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Order> body = response.getBody();
		assertNotNull(body);

		assertEquals(2, body.size());
		for (Order order : body) {
			assertEquals(OrderStatus.DONE, order.getStatus());
		}
	}

	@Test
	void testGetMenu(){
		List<Pizza> menu = new ArrayList<>();
		menu.add(new Pizza(1, "Margherita"));
		when(orderService.getMenu()).thenReturn(menu);
		ResponseEntity<List<Pizza>> response = orderController.getMenu();
		assertEquals(HttpStatus.OK, response.getStatusCode());

		List<Pizza> body = response.getBody();
		assertNotNull(body);

		assertEquals(1, body.size());
	}

	@Test
	void testPrepareOrder() {
		when(orderService.prepareOrder()).thenReturn(OrderStatus.PREPARING);
		ResponseEntity<OrderStatus> response = orderController.prepareOrder();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(OrderStatus.PREPARING, response.getBody());
	}

	@Test
	void testCompleteOrder() {
		when(orderService.completeOrder()).thenReturn(OrderStatus.DONE);
		ResponseEntity<OrderStatus> response = orderController.completeOrder();

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(OrderStatus.DONE, response.getBody());
	}

}
