package ua.khpi.diploma.sangoecommerceclothingproject.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.SizeQuantityDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.SizeQuantity;

import java.util.List;

@Mapper
public interface SizeQuantityMapper {
	SizeQuantityMapper MAPPER = Mappers.getMapper(SizeQuantityMapper.class);

	SizeQuantity toSizeQuantity(SizeQuantityDto dto);

	@InheritInverseConfiguration
	SizeQuantityDto fromSizeQuantity(SizeQuantity sizeQuantity);

	List<SizeQuantity> toSizeQuantityList(List<SizeQuantityDto> dtos);

	List<SizeQuantityDto> fromSizeQuantityList(List<SizeQuantity> list);
}