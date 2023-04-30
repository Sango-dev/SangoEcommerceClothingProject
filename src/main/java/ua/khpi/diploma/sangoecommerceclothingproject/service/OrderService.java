package ua.khpi.diploma.sangoecommerceclothingproject.service;

import org.springframework.data.domain.Pageable;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.OrderDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.order.Order;
import ua.khpi.diploma.sangoecommerceclothingproject.model.order.OrderPage;

public interface OrderService {
    Order saveOrderFromDto(OrderDto orderDto, String username);

    void saveOrder(Order order);

    OrderPage getOrdersByUsernameAndStatus(String username, String status, Pageable pageable);

    OrderPage getOrdersByStatus(String status, Pageable pageable);

    Order getOrderById(String id);

    void createReviewIfNotExist(String username, String orderId);
}
