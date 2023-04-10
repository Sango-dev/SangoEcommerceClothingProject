package ua.khpi.diploma.sangoecommerceclothingproject.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.CategoryDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
	CategoryMapper MAPPER = Mappers.getMapper(CategoryMapper.class);

	Category toCategory(CategoryDto dto);

	@InheritInverseConfiguration
	CategoryDto fromCategory(Category category);

	List<Category> toCategoryList(List<CategoryDto> dtos);

	List<CategoryDto> fromCategoryList(List<Category> list);
}