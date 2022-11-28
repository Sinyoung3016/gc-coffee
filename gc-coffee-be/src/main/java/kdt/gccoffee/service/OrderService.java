package kdt.gccoffee.service;

import kdt.gccoffee.model.Email;
import kdt.gccoffee.model.Order;
import kdt.gccoffee.model.OrderItem;

import java.util.List;

public interface OrderService {
    Order createOrder(Email email, String address, String postcode, List<OrderItem> orderItems);
}
