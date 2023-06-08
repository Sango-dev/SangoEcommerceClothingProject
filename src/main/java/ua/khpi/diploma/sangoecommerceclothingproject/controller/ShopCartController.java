package ua.khpi.diploma.sangoecommerceclothingproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public String showCart(Model model) {
        ShopCartDto dto = cartService.getCartDto();
        model.addAttribute("cart", dto);
        model.addAttribute("totalPrice", dto.getSum());
        return "shopCart";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(params = "store")
    public String saveCart(Principal principal) {
        if (principal != null) {
            User user = userService.findFirstByNickName(principal.getName());
            cartService.storeShopCartToBasket(user);
        }
        return "redirect:/shop-cart";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(params = "load")
    public String loadCart(Principal principal) {
        if (principal != null) {
            User user = userService.findFirstByNickName(principal.getName());
            cartService.loadCartFromBasket(user);
        }
        return "redirect:/shop-cart";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(params = "confirm-order")
    public String commitCartToOrder(Model model, Principal principal) {
        OrderDto dto = cartService.commitCartToOrder(principal.getName());
        model.addAttribute("order", dto);
        return "orderDetails";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(params = "clear")
    public String clearCart() {
        cartService.flushShopCart();
        return "redirect:/shop-cart";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/removeItem")
    public String removeItem(@RequestParam("id") String id, @RequestParam("size") String size) {
        cartService.removeProdFromShopCartByIdAndSize(id, size);
        return "redirect:/shop-cart";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/incamount")
    public String addAmountToShopCart(@RequestParam("id") String id, @RequestParam("size") String size) {
        cartService.updateShopCartProductAmount(id, size, 1);
        return "redirect:/shop-cart";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/decamount")
    public String decAmountToShopCart(@RequestParam("id") String id, @RequestParam("size") String size) {
        cartService.updateShopCartProductAmount(id, size, -1);
        return "redirect:/shop-cart";
    }


}
