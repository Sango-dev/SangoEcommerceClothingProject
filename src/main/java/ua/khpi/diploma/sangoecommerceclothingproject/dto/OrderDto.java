package ua.khpi.diploma.sangoecommerceclothingproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.khpi.diploma.sangoecommerceclothingproject.model.order.DeliveryOptions;
import ua.khpi.diploma.sangoecommerceclothingproject.model.order.OrderStatus;
import ua.khpi.diploma.sangoecommerceclothingproject.model.order.PaymentOptions;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private String id;
    private String email;
    private LocalDate created;
    private LocalDate updated;
    private Double sum;
    private String address;
    private String recipient;
    private String phone;
    private PaymentOptions payment;
    private DeliveryOptions delivery;
    private OrderStatus status;
    private UserDto user;
    private List<OrderDetailsDto> details;
}