package ua.khpi.diploma.sangoecommerceclothingproject.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.SizeClothDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.SizeCloth;

import java.util.List;

@Mapper
public interface SizeClothMapper {
	SizeClothMapper MAPPER = Mappers.getMapper(SizeClothMapper.class);

	SizeCloth toSizeCloth(SizeClothDto dto);

	@InheritInverseConfiguration
  SizeClothDto fromSizeCloth(SizeCloth sizeCloth);

	List<SizeCloth> toSizeClothList(List<SizeClothDto> dtos);

	List<SizeClothDto> fromSizeClothList(List<SizeCloth> list);
}