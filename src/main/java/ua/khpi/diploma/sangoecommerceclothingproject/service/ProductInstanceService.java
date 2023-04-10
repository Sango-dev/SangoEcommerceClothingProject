package ua.khpi.diploma.sangoecommerceclothingproject.service;

import org.springframework.data.domain.Pageable;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.ProductInstanceDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Color;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Gender;
import ua.khpi.diploma.sangoecommerceclothingproject.model.ProductInstancePage;

import java.util.List;

public interface ProductInstanceService {
    ProductInstancePage findAllProductInstances(Pageable pageable);

    ProductInstanceDto findProductInstanceById(String id) throws Exception;

    List<ProductInstanceDto> findAllProductInstancesWithFilters(List<Color> colors, List<String> brands, List<String> categories, List<Gender> genders, List<String> sizes);
}
