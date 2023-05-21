package ua.khpi.diploma.sangoecommerceclothingproject.service;

import ua.khpi.diploma.sangoecommerceclothingproject.dto.CategoryDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Brand;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll();
    void saveCategoryDto(CategoryDto categoryDto);
    void updateCategoryDto(CategoryDto categoryDto);
    Category findCategoryByTitle(String title);
    CategoryDto findCategoryById(String id);
    void deleteCategoryById(String id);
}
