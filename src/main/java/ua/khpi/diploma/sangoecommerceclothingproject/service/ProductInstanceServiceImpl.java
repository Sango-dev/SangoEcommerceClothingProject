package ua.khpi.diploma.sangoecommerceclothingproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.ProductInstanceRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.ProductRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.ProductInstanceDto;
import ua.khpi.diploma.sangoecommerceclothingproject.mapper.ProductInstanceMapper;
import ua.khpi.diploma.sangoecommerceclothingproject.model.*;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.*;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductInstanceServiceImpl implements ProductInstanceService {
    private final ProductInstanceRepository productInstanceRepository;
    private final ProductRepository productRepository;
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
                                                                       List<String> sizes,
                                                                       String sort) {

        List<ProductInstanceCloth> list = productInstanceRepository.findDistinctByColorInAndProduct_Brand_TitleInAndProduct_Category_TitleInAndProduct_GenderInAndSizes_SizeIn(colors, brands, categories, genders, sizes);
        List<ProductInstanceDto> productInstancesDtos = mapper.fromProductInstancesList(list);
        if (!Objects.isNull(sort)) {
            if (sort.equals("price_asc")) {
                productInstancesDtos.sort( (a, b) -> Double.compare(a.getProduct().getPrice(), b.getProduct().getPrice()));
            } else if (sort.equals("price_desc")) {
                productInstancesDtos.sort( (a, b) -> Double.compare(b.getProduct().getPrice(), a.getProduct().getPrice()));
            } else if (sort.equals("date_created_desc")) {
                productInstancesDtos.sort( (a, b) -> b.getDateCreated().compareTo(a.getDateCreated()));
            } else {
                productInstancesDtos.sort( (a, b) -> a.getDateCreated().compareTo(b.getDateCreated()));
            }
        }
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

    @Override
    public List<ProductInstanceDto> findAllProductInstancesBySearchParam(String param) {
        List<ProductInstanceDto> dtos = new ArrayList<>();
        productRepository.findProductsByParam(param.toLowerCase().trim()).stream()
                .forEach(
                        productCloth -> productCloth.getProductInstances().
                                stream().
                                forEach(productInstanceCloth -> dtos.add(mapper.fromProductInstance(productInstanceCloth))
                                )
                );

        return dtos;
    }
}
