package ua.khpi.diploma.sangoecommerceclothingproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, String> {
    @Query(value = "select * from brands b where lower(b.title) like %:title%", nativeQuery = true)
    Brand findBrandByTitle(String title);
}
