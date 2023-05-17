package ua.khpi.diploma.sangoecommerceclothingproject.service;

import ua.khpi.diploma.sangoecommerceclothingproject.dto.CategoryDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Brand;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll();
    void saveCategoryDto(CategoryDto categoryDto);
    Category findCategoryByTitle(String title);
}
