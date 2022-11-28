package kdt.gccoffee.controller;

import kdt.gccoffee.controller.request.CreateOrderRequest;
import kdt.gccoffee.model.Email;
import kdt.gccoffee.model.Order;
import kdt.gccoffee.service.OrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/api/v1/orders")
    public Order createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        return orderService.createOrder(
                new Email(createOrderRequest.email()),
                createOrderRequest.address(),
                createOrderRequest.postcode(),
                createOrderRequest.orderItems()
        );
    }
}
