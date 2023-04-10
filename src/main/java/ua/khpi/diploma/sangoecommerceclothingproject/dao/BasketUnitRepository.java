package ua.khpi.diploma.sangoecommerceclothingproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.khpi.diploma.sangoecommerceclothingproject.model.basket_cart.Basket;
import ua.khpi.diploma.sangoecommerceclothingproject.model.basket_cart.BasketUnit;

@Repository
public interface BasketUnitRepository extends JpaRepository<BasketUnit, String> {
    void deleteAllByBasket(Basket basket);
}
