package ua.khpi.diploma.sangoecommerceclothingproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductCloth;

@Repository
public interface ProductRepository extends JpaRepository<ProductCloth, String> {
}
