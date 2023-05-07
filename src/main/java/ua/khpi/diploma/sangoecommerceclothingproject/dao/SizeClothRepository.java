package ua.khpi.diploma.sangoecommerceclothingproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductInstanceCloth;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.SizeCloth;

import java.util.List;

public interface SizeClothRepository extends JpaRepository<SizeCloth, String> {
    List<SizeCloth> findAllByProductInstance(ProductInstanceCloth productInstanceCloth);
    void deleteBySizeAndProductInstance(String size, ProductInstanceCloth productInstance);
}
