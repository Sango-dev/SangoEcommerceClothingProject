package ua.khpi.diploma.sangoecommerceclothingproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductCloth;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductCloth, String> {
    @Query(value = "select * from products p where lower(p.title) like %:str% or lower(p.description) like %:str%", nativeQuery = true)
    List<ProductCloth> findProductsByParam(@Param("str") String param);
    @Query(value = "select * from products p where lower(p.product_code) like %:code%", nativeQuery = true)
    ProductCloth findProductByProductCode(@Param("code") String productCode);
    ProductCloth findProductClothById(String id);
    ProductCloth findProductClothByProductCode(String productCode);
}
