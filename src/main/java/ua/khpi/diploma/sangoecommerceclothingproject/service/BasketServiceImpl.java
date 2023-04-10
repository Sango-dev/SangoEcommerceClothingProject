package ua.khpi.diploma.sangoecommerceclothingproject.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.BasketRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.BasketUnitRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.model.basket_cart.Basket;
import ua.khpi.diploma.sangoecommerceclothingproject.model.basket_cart.BasketUnit;
import ua.khpi.diploma.sangoecommerceclothingproject.model.basket_cart.ShopCart;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.User;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketRepository basketRepository;
    private final BasketUnitRepository basketUnitRepository;


    @Override
    @Transactional
    public void storeCartToBasket(User user, ShopCart cart) {
        if (user != null && cart != null) {
            Basket basket = user.getBasket();
            if (basket == null) {
                basket = new Basket();
                basket.setUser(user);
            } else {
                basketUnitRepository.deleteAllByBasket(basket);
                basket.getUnits().clear();
            }
            basket.setUnits(cart.getProducts());
            basketRepository.save(basket);
        }
    }

    @Override
    @Transactional
    public void removeBasket(User user) {
        if (user != null) {
            Basket basket = user.getBasket();
            if (basket != null) {
                basketRepository.delete(basket);
            }
        }
    }

    @Override
    @Transactional
    public Basket createBasket(User user) {
       Basket basket = new Basket();
       basket.setUser(user);
       return basketRepository.save(basket);
    }

    @Override
    public List<BasketUnit> getAllFromBasket(User user) {
        List<BasketUnit> units = null;
        if (user != null) {
            Basket basket = user.getBasket();
            if (basket != null) {
                units = new ArrayList<>(basket.getUnits());
                basketUnitRepository.deleteAllByBasket(basket);
                basket.getUnits().clear();
            }
        }
        return units;
    }
}