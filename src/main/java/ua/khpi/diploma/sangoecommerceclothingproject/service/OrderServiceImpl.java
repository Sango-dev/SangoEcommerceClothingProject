package ua.khpi.diploma.sangoecommerceclothingproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.OrderRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.OrderDto;
import ua.khpi.diploma.sangoecommerceclothingproject.mapper.OrderMapper;
import ua.khpi.diploma.sangoecommerceclothingproject.model.order.Order;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper = OrderMapper.MAPPER;
    private final OrderRepository orderRepository;
    private final UserService userService;

    @Override
    public Order saveOrderFromDto(OrderDto orderDto, String username) {
        User user = userService.findFirstByNickName(username);
        Order order = orderMapper.toOrder(orderDto);
        order.setUser(user);
        order.getDetails().stream()
                .forEach(detail -> detail.setOrder(order));
        return orderRepository.save(order);
    }

    @Override
    public List<OrderDto> getOrdersByUsername(String username) {
        /*if (nickname.isBlank()) {
            throw new RuntimeException("Nickname must not be empty or blank!!!");
        }*/
        return orderMapper.fromOrderList(userService.findFirstByNickName(username).getOrders());
    }
}
