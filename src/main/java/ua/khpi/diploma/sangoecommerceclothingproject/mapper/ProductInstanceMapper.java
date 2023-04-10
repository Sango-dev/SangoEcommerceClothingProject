package ua.khpi.diploma.sangoecommerceclothingproject.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.ProductInstanceDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductInstanceCloth;

import java.util.List;

@Mapper
public interface ProductInstanceMapper {
	ProductInstanceMapper MAPPER = Mappers.getMapper(ProductInstanceMapper.class);

	ProductInstanceCloth toProductInstance(ProductInstanceDto dto);

	@InheritInverseConfiguration
	ProductInstanceDto fromProductInstance(ProductInstanceCloth productInstance);

	List<ProductInstanceCloth> toProductInstancesList(List<ProductInstanceDto> dtos);

	List<ProductInstanceDto> fromProductInstancesList(List<ProductInstanceCloth> list);
}