package ua.khpi.diploma.sangoecommerceclothingproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.OrderDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.order.Order;
import ua.khpi.diploma.sangoecommerceclothingproject.model.order.OrderPage;
import ua.khpi.diploma.sangoecommerceclothingproject.model.order.OrderStatus;
import ua.khpi.diploma.sangoecommerceclothingproject.service.OrderService;

import java.security.Principal;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/save-order")
    public String saveOrder(OrderDto orderDto, Principal principal) {
        String username = principal.getName();
        orderService.saveOrderFromDto(orderDto, username);
        return "redirect:/order/order-history?status=NEW";
    }

    @GetMapping("/order-history")
    public String getOrderHistoryByUsername(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = true) String status,
            @RequestParam(required = false) String sort,
            Principal principal,
            Model model
    ) {
        int size = 5;
        OrderPage orderPage;
        if (sort != null) {
            Sort sortBy = null;
            if (sort.equals("sum-asc")) {
                sortBy = Sort.by(Sort.Direction.ASC, "sum");
            }
            if (sort.equals("sum-desc")) {
                sortBy = Sort.by(Sort.Direction.DESC, "sum");
            }
            if (sort.equals("date-asc")) {
                sortBy = Sort.by(Sort.Direction.ASC, "updated");
            }
            if (sort.equals("date-desc")) {
                sortBy = Sort.by(Sort.Direction.DESC, "updated");
            }

            orderPage = orderService.getOrdersByUsernameAndStatus(principal.getName(), status, PageRequest.of(page, size, sortBy));
        } else {
            orderPage = orderService.getOrdersByUsernameAndStatus(principal.getName(), status, PageRequest.of(page, size));
        }
        model.addAttribute("orders", orderPage.getOrderDtos());
        model.addAttribute("totalPages", orderPage.getTotalPages());
        model.addAttribute("currentPage", page);
        return "orderHistory";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("update-status")
    public String getOrdersByOrderStatus(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = true) String status,
            @RequestParam(required = false) String sort,
            Model model
    ) {

        int size = 5;
        OrderPage orderPage;
        if (sort != null) {
            Sort sortBy = null;
            if (sort.equals("sum-asc")) {
                sortBy = Sort.by(Sort.Direction.ASC, "sum");
            }
            if (sort.equals("sum-desc")) {
                sortBy = Sort.by(Sort.Direction.DESC, "sum");
            }
            if (sort.equals("date-asc")) {
                sortBy = Sort.by(Sort.Direction.ASC, "updated");
            }
            if (sort.equals("date-desc")) {
                sortBy = Sort.by(Sort.Direction.DESC, "updated");
            }

            orderPage = orderService.getOrdersByStatus(status, PageRequest.of(page, size, sortBy));
        } else {
            orderPage = orderService.getOrdersByStatus(status, PageRequest.of(page, size));
        }
        model.addAttribute("orders", orderPage.getOrderDtos());
        model.addAttribute("totalPages", orderPage.getTotalPages());
        model.addAttribute("currentPage", page);
        return "handlingOrders";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("update-status")
    public String updateOrderStatus(
            @RequestParam String id,
            @RequestParam String status
    ) {

        Order order = orderService.getOrderById(id);
        String username = order.getUser().getNickName();
        if (order != null) {
            order.setStatus(OrderStatus.valueOf(status));
            orderService.saveOrder(order);
        }

        if (status.equals("COMPLETED")) {
            orderService.createReviewIfNotExist(username, id);
        }

        return "redirect:/order/update-status?status=" + status.toUpperCase() + "&sort=date-desc";
    }

}
