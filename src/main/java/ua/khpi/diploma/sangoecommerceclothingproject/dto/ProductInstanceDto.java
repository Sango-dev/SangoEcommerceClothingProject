package ua.khpi.diploma.sangoecommerceclothingproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Color;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductInstanceLink;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInstanceDto {
    private String id;
    private String linkOfMainPicture;
    private String linkOfFrontPicture;
    private String linkOfBackPicture;
    private Boolean available;
    private Color color;
    private String colorDefinition;
    private LocalDate dateCreated;
    private ProductDto product;
    private List<SizeQuantityDto> sizes;
    List<ProductInstanceLink> links;
}
