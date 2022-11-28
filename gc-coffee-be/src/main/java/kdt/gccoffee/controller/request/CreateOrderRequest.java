package kdt.gccoffee.controller.request;

import kdt.gccoffee.model.OrderItem;

import java.util.List;

public record CreateOrderRequest(String email, String address, String postcode, List<OrderItem> orderItems) {
}
