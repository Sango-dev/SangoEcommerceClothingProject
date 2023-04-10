package ua.khpi.diploma.sangoecommerceclothingproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
}
