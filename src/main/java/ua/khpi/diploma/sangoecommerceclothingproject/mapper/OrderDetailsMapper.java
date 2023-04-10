package ua.khpi.diploma.sangoecommerceclothingproject.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.OrderDetailsDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.order.OrderDetails;

import java.util.List;

@Mapper
public interface OrderDetailsMapper {
    OrderDetailsMapper MAPPER = Mappers.getMapper(OrderDetailsMapper.class);

    OrderDetails toOrderDetails(OrderDetailsDto dto);

    @InheritInverseConfiguration
    OrderDetailsDto fromOrderDetails(OrderDetails orderDetail);

    List<OrderDetails> toOrderDetailsList(List<OrderDetailsDto> dtos);

    List<OrderDetailsDto> fromOrderDetailsList(List<OrderDetails> list);
}