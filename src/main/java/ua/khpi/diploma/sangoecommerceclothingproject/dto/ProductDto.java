package ua.khpi.diploma.sangoecommerceclothingproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.Gender;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String id;
    private String productCode;
    private String title;
    private String description;
    private String composition;
    private Gender gender;
    private Boolean available;
    private Double price;
    private Boolean isUnderwear;
    private BrandDto brand;
    private CategoryDto category;
}
