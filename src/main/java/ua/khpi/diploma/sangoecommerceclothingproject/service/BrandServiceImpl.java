package ua.khpi.diploma.sangoecommerceclothingproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.BrandRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.BrandDto;
import ua.khpi.diploma.sangoecommerceclothingproject.mapper.BrandMapper;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Brand;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService{
    private final BrandRepository brandRepository;
    private final BrandMapper mapper = BrandMapper.MAPPER;

    @Override
    public List<BrandDto> findAll() {
        return mapper.fromBrandList(brandRepository.findAll());
    }

    @Override
    public void saveBrandDto(BrandDto brandDto) {
        Brand brand = new Brand();
        brand.setTitle(brandDto.getTitle());
        brandRepository.save(brand);
    }
}
