package ua.khpi.diploma.sangoecommerceclothingproject.model;

import lombok.Getter;
import lombok.Setter;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.ProductInstanceDto;

import java.util.List;

@Getter
@Setter
public class ProductInstancePage {
    private final List<ProductInstanceDto> productInstanceDtos;
    private final int totalPages;

    public ProductInstancePage(List<ProductInstanceDto> productInstanceDtos, int totalPages) {
        this.productInstanceDtos = productInstanceDtos;
        this.totalPages = totalPages;
    }

}