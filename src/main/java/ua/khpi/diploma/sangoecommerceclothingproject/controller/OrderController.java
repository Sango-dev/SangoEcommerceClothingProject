package ua.khpi.diploma.sangoecommerceclothingproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.OrderDto;
import ua.khpi.diploma.sangoecommerceclothingproject.service.OrderService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/save-order")
    public String saveOrder(OrderDto orderDto, Principal principal) {
        String username = principal.getName();
        orderService.saveOrderFromDto(orderDto, username);
        return "redirect:/";
    }

    @GetMapping("/order-history")
    public String getOrderHistoryByUsername(Principal principal, Model model) {
        List<OrderDto> dtos = orderService.getOrdersByUsername(principal.getName());
        model.addAttribute("orders", dtos);
        return "orderHistory";
    }
}
