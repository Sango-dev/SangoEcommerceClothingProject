package ua.khpi.diploma.sangoecommerceclothingproject.service;

import ua.khpi.diploma.sangoecommerceclothingproject.dto.ProductDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductCloth;

public interface ProductClothService {
    ProductCloth getProductClothById(String id);
    ProductDto getProductDtoById(String id);
    void updateProduct(String prodId, ProductDto productDto);
    void addProduct(ProductDto productDto);
    ProductCloth findFirstByProductCode(String productCode);
    void deleteProductById(String id);
}
