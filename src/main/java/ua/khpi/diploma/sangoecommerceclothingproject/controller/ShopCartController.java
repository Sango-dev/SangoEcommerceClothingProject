package ua.khpi.diploma.sangoecommerceclothingproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.OrderDto;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.ShopCartDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.User;
import ua.khpi.diploma.sangoecommerceclothingproject.service.ShopCartService;
import ua.khpi.diploma.sangoecommerceclothingproject.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/shop-cart")
@RequiredArgsConstructor
public class ShopCartController {
    private final ShopCartService cartService;
    private final UserService userService;

    @GetMapping
    public String showCart(Model model) {
        ShopCartDto dto = cartService.getCartDto();
        model.addAttribute("cart", dto);
        model.addAttribute("totalPrice", dto.getSum());
        return "shopCart";
    }

    @PostMapping(params = "store")
    public String saveCart(Principal principal) {
        if (principal != null) {
            User user = userService.findFirstByNickName(principal.getName());
            cartService.storeShopCartToBasket(user);
        }
        return "redirect:/shop-cart";
    }

    @PostMapping(params = "load")
    public String loadCart(Principal principal) {
        if (principal != null) {
            User user = userService.findFirstByNickName(principal.getName());
            cartService.loadCartFromBasket(user);
        }
        return "redirect:/shop-cart";
    }

    @PostMapping("/confirmation-order")
    public String commitCartToOrder(Model model) {
        OrderDto dto = cartService.commitCartToOrder();
        model.addAttribute("order", dto);
        return "orderDetails";
    }
}
