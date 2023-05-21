package ua.khpi.diploma.sangoecommerceclothingproject.service;

import ua.khpi.diploma.sangoecommerceclothingproject.dto.BrandDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Brand;

import java.util.List;

public interface BrandService {
    List<BrandDto> findAll();
    void saveBrandDto(BrandDto brandDto);
    Brand findBrandByTitle(String title);
    BrandDto findBrandById(String id);
    void updateBrandDto(BrandDto brandDto);
    void deleteBrandById(String id);
}
