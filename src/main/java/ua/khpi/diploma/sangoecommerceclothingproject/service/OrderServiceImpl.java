package ua.khpi.diploma.sangoecommerceclothingproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.OrderRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.OrderDto;
import ua.khpi.diploma.sangoecommerceclothingproject.mapper.OrderMapper;
import ua.khpi.diploma.sangoecommerceclothingproject.model.order.Order;
import ua.khpi.diploma.sangoecommerceclothingproject.model.order.OrderPage;
import ua.khpi.diploma.sangoecommerceclothingproject.model.order.OrderStatus;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductCloth;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.User;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderMapper orderMapper = OrderMapper.MAPPER;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductInstanceService productInstanceService;
    private final ProductClothService productClothService;
    private final ReviewService reviewService;
    private final ShopCartService shopCartService;


    @Override
    public Order saveOrderFromDto(OrderDto orderDto, String username) {
        User user = userService.findFirstByNickName(username);
        Order order = orderMapper.toOrder(orderDto);
        order.setUser(user);
        order.getDetails().stream()
                .forEach(detail -> detail.setOrder(order));
        shopCartService.flushShopCart();
        return orderRepository.save(order);
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public OrderPage getOrdersByUsernameAndStatus(String username, String status, Pageable pageable) {

        /*if (nickname.isBlank()) {
            throw new RuntimeException("Nickname must not be empty or blank!!!");
        }*/

        OrderStatus orderStatus = OrderStatus.valueOf(status);
        User user = userService.findFirstByNickName(username);

        final Page<Order> byUserAndStatus = orderRepository.findByUserAndStatus(user, orderStatus, pageable);
        List<OrderDto> list = orderMapper.fromOrderList(byUserAndStatus.getContent());
        int totalPages = byUserAndStatus.getTotalPages();
        return new OrderPage(list, totalPages);

        //return orderMapper.fromOrderList(userService.findFirstByNickName(username).getOrders().stream().filter(order -> order.getStatus().equals(orderStatus)).collect(Collectors.toList()));
    }

    @Override
    public OrderPage getOrdersByStatus(String status, Pageable pageable) {
        OrderStatus orderStatus = OrderStatus.valueOf(status);

        final Page<Order> byStatus = orderRepository.findByStatus(orderStatus, pageable);
        List<OrderDto> list = orderMapper.fromOrderList(byStatus.getContent());
        int totalPages = byStatus.getTotalPages();
        return new OrderPage(list, totalPages);
    }

    @Override
    public Order getOrderById(String id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public void createReviewIfNotExist(String username, String orderId) {
        Order order = orderRepository.findById(orderId).get();
        User user = userService.findFirstByNickName(username);

        order.getDetails()
                .stream()
                .map(orderDetails -> productInstanceService.getIdOfProductCloth(orderDetails.getProductInstanceId()))
                .distinct()
                .map(id -> productClothService.getProductClothById(id))
                .filter(productCloth -> reviewService.getReviewByProductAndUser(productCloth, user) == null)
                .forEach(productCloth -> reviewService.saveReviewByProductAndUser(productCloth, user));
    }
}
