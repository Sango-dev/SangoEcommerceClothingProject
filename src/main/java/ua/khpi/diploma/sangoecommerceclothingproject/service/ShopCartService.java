package ua.khpi.diploma.sangoecommerceclothingproject.service;

import ua.khpi.diploma.sangoecommerceclothingproject.dto.OrderDto;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.ShopCartDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.basket_cart.ShopCart;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.User;

public interface ShopCartService {
    ShopCart attachToShopCart(String id, String size, String nickname);
    void flushShopCart();
    ShopCartDto getCartDto();
    void storeShopCartToBasket(User user);
    void loadCartFromBasket(User user);
    OrderDto commitCartToOrder(String nickname);
    void removeProdFromShopCartByIdAndSize(String id, String size);
    void updateShopCartProductAmount(String id, String size, final long amountDif);
}
