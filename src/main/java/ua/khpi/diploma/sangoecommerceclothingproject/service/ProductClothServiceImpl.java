package ua.khpi.diploma.sangoecommerceclothingproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.BrandRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.CategoryRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.ProductRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.ProductDto;
import ua.khpi.diploma.sangoecommerceclothingproject.mapper.ProductMapper;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductCloth;

@Service
@RequiredArgsConstructor
public class ProductClothServiceImpl implements ProductClothService{
    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper = ProductMapper.MAPPER;


    @Override
    public ProductCloth getProductClothById(String id) {
        return productRepository.findProductClothById(id);
    }

    @Override
    public ProductDto getProductDtoById(String id) {
        return productMapper.fromProduct(productRepository.findProductClothById(id));
    }

    @Override
    public void updateProduct(String prodId, ProductDto productDto) {
        ProductCloth product = productRepository.findProductClothById(prodId);
        if (product == null) {
            throw new RuntimeException("Product is not found with ID : " + prodId);
        }

        product.setProductCode(productDto.getProductCode());
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setComposition(productDto.getComposition());
        product.setGender(productDto.getGender());
        product.setPrice(productDto.getPrice());
        product.setIsUnderwear(productDto.getIsUnderwear());
        product.setBrand(brandRepository.findById(productDto.getBrand().getId()).get());
        product.setCategory(categoryRepository.findById(productDto.getCategory().getId()).get());
        productRepository.save(product);
    }

    @Override
    public void addProduct(ProductDto productDto) {
        ProductCloth productCloth = productMapper.toProduct(productDto);
        productCloth.setBrand(brandRepository.findById(productDto.getBrand().getId()).get());
        productCloth.setCategory(categoryRepository.findById(productDto.getCategory().getId()).get());
        productRepository.save(productCloth);
    }
}
