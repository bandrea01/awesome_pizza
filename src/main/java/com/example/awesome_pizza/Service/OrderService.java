package com.example.awesome_pizza.Service;

import com.example.awesome_pizza.Model.Order;
import com.example.awesome_pizza.Model.OrderStatus;
import com.example.awesome_pizza.Model.Pizza;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    //Data structures for saving datas
    private final Map<Integer, String> menu = new HashMap<>();
    private final Map<String, Order> orders = new HashMap<>();
    private final Queue<Order> pendingOrders = new LinkedList<>();

    public OrderService() {
        menu.put(1, "Margherita");
        menu.put(2, "Marinara");
        menu.put(3, "Funghi");
        menu.put(4, "Quattro Formaggi");
        menu.put(5, "Estate");
    }

    //Creation of a new order
    public String createOrder(List<Integer> pizzasId) {
        List<Pizza> pizzas = new ArrayList<>();
        for (Integer id : pizzasId) {
            String pizzaName = menu.get(id);
            if (pizzaName != null) {
                Pizza pizza = new Pizza(id, pizzaName);
                pizzas.add(pizza);
            }
            else {
                return null;
            }
        }

        Order order = new Order(generateUniqueCode(), pizzas, OrderStatus.PENDING);
        orders.put(order.getOrderId(), order);
        pendingOrders.add(order);

        return order.getOrderId();
    }

    //Retrieving of a single order by id
    public Order getOrder(String orderId) {
        return orders.get(orderId);
    }

    //Retrieving of all orders
    public List<Order> getAllOrders() {
        return new ArrayList<>(orders.values());
    }

    //Retrieving of pending orders
    public List<Order> getPendingOrders() {
        return new ArrayList<>(pendingOrders);
    }

    //Retrieving of completed orders
    public List<Order> getDoneOrders() {
        List<Order> doneOrders = new ArrayList<>();
        for (Order order : orders.values()) {
            if (order.getStatus() == OrderStatus.DONE){
                doneOrders.add(order);
            }
        }
        return doneOrders;
    }

    //Retrieving menu
    public List<Pizza> getMenu() {
        List<Pizza> menuItems = new ArrayList<>();
        for (Map.Entry<Integer, String> entry : menu.entrySet()) {
            Pizza menuItem = new Pizza(entry.getKey(), entry.getValue());
            menuItems.add(menuItem);
        }
        return menuItems;
    }

    //Update of first order in FIFO queue to PREPARING
    public OrderStatus prepareOrder() {
        Order order = pendingOrders.peek();
        if (order != null) {
            orders.get(order.getOrderId()).setStatus(OrderStatus.PREPARING);
            pendingOrders.remove();
            return OrderStatus.PREPARING;
        }
        return null;
    }

    //Update of first order in FIFO queue to DONE
    public OrderStatus completeOrder() {
        Order order = pendingOrders.peek();
        if (order != null) {
            orders.get(order.getOrderId()).setStatus(OrderStatus.DONE);
            pendingOrders.remove();
            return OrderStatus.DONE;
        }
        return null;
    }

    //Creation of a unique ID
    public static String generateUniqueCode() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
