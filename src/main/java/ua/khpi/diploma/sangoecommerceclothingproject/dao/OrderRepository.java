package ua.khpi.diploma.sangoecommerceclothingproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.khpi.diploma.sangoecommerceclothingproject.model.order.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

}
