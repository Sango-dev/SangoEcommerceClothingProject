package ua.khpi.diploma.sangoecommerceclothingproject.model.order;

import lombok.Getter;
import lombok.Setter;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.OrderDto;

import java.util.List;

@Getter
@Setter
public class OrderPage {
    private final List<OrderDto> orderDtos;
    private final int totalPages;

    public OrderPage(List<OrderDto> orderDtos, int totalPages) {
        this.orderDtos = orderDtos;
        this.totalPages = totalPages;
    }

}