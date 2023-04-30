package ua.khpi.diploma.sangoecommerceclothingproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.ProductRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductCloth;

@Service
@RequiredArgsConstructor
public class ProductClothServiceImpl implements ProductClothService{
    private final ProductRepository productRepository;

    @Override
    public ProductCloth getProductClothById(String id) {
        return productRepository.findProductClothById(id);
    }
}
