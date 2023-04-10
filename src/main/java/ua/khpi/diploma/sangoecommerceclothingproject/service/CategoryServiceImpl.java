package ua.khpi.diploma.sangoecommerceclothingproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.CategoryRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.CategoryDto;
import ua.khpi.diploma.sangoecommerceclothingproject.mapper.CategoryMapper;

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
}
