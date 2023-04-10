package ua.khpi.diploma.sangoecommerceclothingproject.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.ProductDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductCloth;

import java.util.List;

@Mapper
public interface ProductMapper {
	ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

	ProductCloth toProduct(ProductDto dto);

	@InheritInverseConfiguration
	ProductDto fromProduct(ProductCloth product);

	List<ProductCloth> toProductList(List<ProductDto> dtos);

	List<ProductDto> fromProductList(List<ProductCloth> list);
}