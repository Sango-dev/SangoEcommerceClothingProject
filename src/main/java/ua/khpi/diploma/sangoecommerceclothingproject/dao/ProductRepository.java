package ua.khpi.diploma.sangoecommerceclothingproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductCloth;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductCloth, String> {
    @Query(value = "select * from products p where lower(p.title) like %:str% or lower(p.description) like %:str% or lower(p.composition) like %:str%", nativeQuery = true)
    List<ProductCloth> findProductsByParam(@Param("str") String param);

    ProductCloth findProductClothById(String id);
}
