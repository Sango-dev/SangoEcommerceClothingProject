package ua.khpi.diploma.sangoecommerceclothingproject.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.BrandDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Brand;

import java.util.List;

@Mapper
public interface BrandMapper {
	BrandMapper MAPPER = Mappers.getMapper(BrandMapper.class);

	Brand toBrand(BrandDto dto);

	@InheritInverseConfiguration
	BrandDto fromBrand(Brand brand);

	List<Brand> toBrandList(List<BrandDto> dtos);

	List<BrandDto> fromBrandList(List<Brand> list);
}