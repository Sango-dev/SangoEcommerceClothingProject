package ua.khpi.diploma.sangoecommerceclothingproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.CategoryRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.CategoryDto;
import ua.khpi.diploma.sangoecommerceclothingproject.mapper.CategoryMapper;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Category;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper mapper = CategoryMapper.MAPPER;

    @Override
    public List<CategoryDto> findAll() {
        return mapper.fromCategoryList(categoryRepository.findAll());
    }

    @Override
    public void saveCategoryDto(CategoryDto categoryDto) {
        Category category = new Category();
        category.setTitle(categoryDto.getTitle());
        categoryRepository.save(category);
    }

    @Override
    public void updateCategoryDto(CategoryDto categoryDto) {
        Category category = categoryRepository.findCategoryById(categoryDto.getId());
        category.setTitle(categoryDto.getTitle());
        categoryRepository.save(category);
    }

    @Override
    public Category findCategoryByTitle(String title) {
        return categoryRepository.findCategoryByTitle(title.toLowerCase());
    }

    @Override
    public CategoryDto findCategoryById(String id) {
        return mapper.fromCategory(categoryRepository.findCategoryById(id));
    }

    @Override
    @Transactional
    public void deleteCategoryById(String id) {
        categoryRepository.deleteById(id);
    }
}
