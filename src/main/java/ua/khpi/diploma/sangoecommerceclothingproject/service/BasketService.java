package ua.khpi.diploma.sangoecommerceclothingproject.service;

import ua.khpi.diploma.sangoecommerceclothingproject.model.basket_cart.Basket;
import ua.khpi.diploma.sangoecommerceclothingproject.model.basket_cart.BasketUnit;
import ua.khpi.diploma.sangoecommerceclothingproject.model.basket_cart.ShopCart;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.User;

import java.util.List;

public interface BasketService {

    void storeCartToBasket(User user, ShopCart cart);

    List<BasketUnit> getAllFromBasket(User user);

    void removeBasket(User user);

    Basket createBasket(User user);
}
