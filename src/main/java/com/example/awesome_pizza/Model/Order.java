package com.example.awesome_pizza.Model;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private List<Pizza> pizzas;
    private OrderStatus status;

    public Order(String orderId, List<Pizza> pizzas, OrderStatus status) {
        this.orderId = orderId;
        this.pizzas = pizzas;
        this.status = status;
    }

    public Order() {
        this.orderId = null;
        this.pizzas = new ArrayList<>();
        this.status = null;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void setPizzas(List<Pizza> pizzas) {
        this.pizzas = pizzas;
    }

    public void addPizzaToOrder(Pizza pizza){
        this.pizzas.add(pizza);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}


