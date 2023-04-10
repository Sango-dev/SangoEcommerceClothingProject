package ua.khpi.diploma.sangoecommerceclothingproject.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.OrderDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.order.Order;

import java.util.List;

@Mapper
public interface OrderMapper {
    OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);

    Order toOrder(OrderDto dto);

    @InheritInverseConfiguration
    OrderDto fromOrder(Order order);

    List<Order> toOrderList(List<OrderDto> dtos);

    List<OrderDto> fromOrderList(List<Order> list);
}
