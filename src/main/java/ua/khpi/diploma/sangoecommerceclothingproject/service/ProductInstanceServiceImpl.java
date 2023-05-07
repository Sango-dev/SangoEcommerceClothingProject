package ua.khpi.diploma.sangoecommerceclothingproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.ProductInstanceRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.ProductRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.ProdInstanceIdPic;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.ProductDto;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.ProductInstanceDto;
import ua.khpi.diploma.sangoecommerceclothingproject.mapper.ProductInstanceMapper;
import ua.khpi.diploma.sangoecommerceclothingproject.mapper.ProductMapper;
import ua.khpi.diploma.sangoecommerceclothingproject.model.*;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductInstanceServiceImpl implements ProductInstanceService {
    private final ProductInstanceRepository productInstanceRepository;
    private final ProductRepository productRepository;
    private final ProductInstanceMapper mapper = ProductInstanceMapper.MAPPER;
    private final ProductMapper productMapper = ProductMapper.MAPPER;

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
    public String getIdOfProductCloth(String id) {
        return productInstanceRepository.findProductInstanceById(id).getProduct().getId();
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

    @Override
    public List<ProductDto> findAllProductsWithInstances() {
        List<ProductCloth> productList = productRepository.findAll();
        List<ProductDto> dtos = productMapper.fromProductList(productList);
        int sz = productList.size();
        for (int i = 0; i < sz; i++ ) {
            List<ProductInstanceCloth> listProdInst = productList.get(i).getProductInstances();
            List<ProdInstanceIdPic> listProdIdPic = new ArrayList<>();
            listProdInst.stream().forEach(pi ->
                    listProdIdPic.add(new ProdInstanceIdPic(pi.getId(), pi.getLinkOfMainPicture()))
            );
            dtos.get(i).setProdInstIdPic(listProdIdPic);
        }

        return dtos;
    }

    @Override
    public ProductInstanceDto findProductInsClothById(String id) {
        return mapper.fromProductInstance(productInstanceRepository.findProductInstanceById(id));
    }

    @Override
    public void updateProductInstance(String id, ProductInstanceDto productInstanceDto) {
        ProductInstanceCloth productInstance = productInstanceRepository.findProductInstanceById(id);
        if (productInstance == null) {
            throw new RuntimeException("Product is not found with ID : " + id);
        }
        productInstance.setLinkOfMainPicture(productInstanceDto.getLinkOfMainPicture());
        productInstance.setLinkOfFrontPicture(productInstanceDto.getLinkOfFrontPicture());
        productInstance.setLinkOfBackPicture(productInstanceDto.getLinkOfBackPicture());
        productInstance.setAvailable(productInstanceDto.getAvailable());
        productInstance.setColor(productInstanceDto.getColor());
        productInstance.setColorDefinition(productInstanceDto.getColorDefinition());
        productInstanceRepository.save(productInstance);
    }

    @Override
    public void addProductInstance(String id, ProductInstanceDto productInstanceDto) {
        ProductCloth productCloth = productRepository.findProductClothById(id);
        ProductInstanceCloth productInstanceCloth = mapper.toProductInstance(productInstanceDto);
        productInstanceCloth.setId(UUID.randomUUID().toString());
        productInstanceCloth.setProduct(productCloth);
        productInstanceCloth.setDateCreated(LocalDate.now());
        productCloth.getProductInstances().add(productInstanceRepository.save(productInstanceCloth));
    }
}
