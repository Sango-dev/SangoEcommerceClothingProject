package ua.khpi.diploma.sangoecommerceclothingproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.ProductInstanceRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.ProductInstanceDto;
import ua.khpi.diploma.sangoecommerceclothingproject.mapper.ProductInstanceMapper;
import ua.khpi.diploma.sangoecommerceclothingproject.model.*;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Color;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Gender;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductInstanceCloth;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductInstanceLink;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductInstanceServiceImpl implements ProductInstanceService {
    private final ProductInstanceRepository productInstanceRepository;
    private final ProductInstanceMapper mapper = ProductInstanceMapper.MAPPER;

    @Override
    public ProductInstancePage findAllProductInstances(Pageable pageable) {
        Page<ProductInstanceCloth> pageOfProductInstances = productInstanceRepository.findAll(pageable);
        List<ProductInstanceDto> productInstancesDtos = mapper.fromProductInstancesList(pageOfProductInstances.getContent());
        return new ProductInstancePage(productInstancesDtos, pageOfProductInstances.getTotalPages());
    }

    @Override
    public List<ProductInstanceDto> findAllProductInstancesWithFilters(List<Color> colors,
                                               List<String> brands,
                                               List<String> categories,
                                               List<Gender> genders,
                                               List<String> sizes) {

        List<ProductInstanceCloth> list = productInstanceRepository.findDistinctByColorInAndProduct_Brand_TitleInAndProduct_Category_TitleInAndProduct_GenderInAndSizes_SizeIn(colors, brands, categories, genders, sizes);
        List<ProductInstanceDto> productInstancesDtos = mapper.fromProductInstancesList(list);
        return productInstancesDtos;
    }

    @Override
    public ProductInstanceDto findProductInstanceById(String id) throws Exception {
        ProductInstanceCloth productInstance = productInstanceRepository.findProductInstanceById(id);
        if (productInstance == null) {
            throw new Exception("ProductCloth Instance not exist with this id!!!");
        }

        ProductInstanceDto dto = mapper.fromProductInstance(productInstance);

        dto.setLinks(productInstance.getProduct().getProductInstances().stream()
                .filter(pi -> pi.getAvailable() != false && pi.getId() != dto.getId())
                .map(pi -> new ProductInstanceLink(pi.getId(), pi.getColorDefinition()))
                .collect(Collectors.toList()));

        return dto;
    }

}
