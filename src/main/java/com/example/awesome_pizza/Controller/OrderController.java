package com.example.awesome_pizza.Controller;

import com.example.awesome_pizza.Model.Order;
import com.example.awesome_pizza.Model.OrderStatus;
import com.example.awesome_pizza.Model.Pizza;
import com.example.awesome_pizza.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController (OrderService orderService) {
        this.orderService = orderService;
    }

    // Order creation: POST
    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody List<Integer> pizzasId) {
        String orderID = orderService.createOrder(pizzasId);
        if (orderID == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: pizza ID not found");
        }
        return ResponseEntity.ok(orderID);
    }

    //Single order retrieving: GET
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrder(@PathVariable String orderId) {
        Order order = orderService.getOrder(orderId);
        if (order == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Order '" + orderId + "' not found");
        }
        return ResponseEntity.ok(order);
    }

    //All orders retrieving: GET
    @GetMapping("/all")
    public ResponseEntity<?> getAllOrders() {
        List<Order> ordersList = orderService.getAllOrders();
        if (ordersList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("There is no orders!");
        }
        return ResponseEntity.ok(ordersList);
    }

    //All pending orders retrieving: GET
    @GetMapping("/pending")
    public ResponseEntity<List<Order>> getAllPendingOrders() {
        List<Order> pendingOrdersList = orderService.getPendingOrders();
        return ResponseEntity.ok(pendingOrdersList);
    }

    //All done orders retrieving: GET
    @GetMapping("/done")
    public ResponseEntity<List<Order>> getAllDoneOrders() {
        List<Order> doneOrdersList = orderService.getDoneOrders();
        return ResponseEntity.ok(doneOrdersList);
    }

    //Menu retrieving: GET
    @GetMapping("/menu")
    public ResponseEntity<List<Pizza>> getMenu() {
        List<Pizza> menu = orderService.getMenu();
        return ResponseEntity.ok(menu);
    }

    //Order preparation call: PATCH
    @PatchMapping("/prepare")
    public ResponseEntity<OrderStatus> prepareOrder(){
        OrderStatus newStatus = orderService.prepareOrder();
        return ResponseEntity.ok(newStatus);
    }

    //Order completion call: PATCH
    @PatchMapping("/complete")
    public ResponseEntity<OrderStatus> completeOrder(){
        OrderStatus newStatus = orderService.completeOrder();
        return ResponseEntity.ok(newStatus);
    }


}
