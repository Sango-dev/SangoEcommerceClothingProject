package ua.khpi.diploma.sangoecommerceclothingproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.khpi.diploma.sangoecommerceclothingproject.model.basket_cart.Basket;

@Repository
public interface BasketRepository extends JpaRepository<Basket, String> {
}
