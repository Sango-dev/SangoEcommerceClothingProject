package ua.khpi.diploma.sangoecommerceclothingproject.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Color;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Gender;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductInstanceCloth;

import java.util.List;

@Repository
public interface ProductInstanceRepository extends JpaRepository<ProductInstanceCloth, String> {
  Page<ProductInstanceCloth> findAllByAvailableTrue(Pageable pageable);
  List<ProductInstanceCloth> findDistinctByAvailableTrueAndColorInAndProduct_Brand_TitleInAndProduct_Category_TitleInAndProduct_GenderInAndSizes_SizeIn(
          List<Color> colors, List<String> brands, List<String> categories, List<Gender> genders, List<String> sizes);

  ProductInstanceCloth findProductInstanceById(String id);
}
