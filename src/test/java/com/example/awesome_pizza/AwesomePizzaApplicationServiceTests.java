package com.example.awesome_pizza;

import com.example.awesome_pizza.Model.Order;
import com.example.awesome_pizza.Model.Pizza;
import com.example.awesome_pizza.Service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AwesomePizzaApplicationServiceTests {

    private OrderService service;
    private String testOrderId;

    @BeforeEach
    void setUp() {
        service = new OrderService();
        List<Integer> pizzasId = Arrays.asList(1, 2, 3);
        testOrderId = service.createOrder(pizzasId);
    }

    @Test
    void testGetOrder() {
        Order order = service.getOrder(testOrderId);
        assertNotNull(order);
        assertEquals(testOrderId, order.getOrderId());
    }

    @Test
    void testGetAllOrder() {
        List<Order> orders = service.getAllOrders();
        assertNotNull(orders);
        assertEquals(1, orders.size());
    }

    @Test
    void testGetAllPendingOrders() {
        List<Order> orders = service.getPendingOrders();
        assertNotNull(orders);
        assertEquals(1, orders.size());
    }

    @Test
    void testGetAllDoneOrders() {
        List<Order> orders = service.getDoneOrders();
        assertNotNull(orders);
        assertEquals(0, orders.size());
    }
}
