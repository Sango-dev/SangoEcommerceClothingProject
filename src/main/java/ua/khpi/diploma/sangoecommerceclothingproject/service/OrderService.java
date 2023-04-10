package ua.khpi.diploma.sangoecommerceclothingproject.service;

import ua.khpi.diploma.sangoecommerceclothingproject.dto.OrderDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.order.Order;

import java.util.List;

public interface OrderService {
    Order saveOrderFromDto(OrderDto orderDto, String username);
    List<OrderDto> getOrdersByUsername(String username);
}
