package ua.khpi.diploma.sangoecommerceclothingproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductInstanceCloth;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCartDetailDto {
    private String productInstanceId;
    private String title;
    private String linkOfPicture;
    private Double price;
    private String size;
    private Long amount;
    private Double sum;

    public ShopCartDetailDto(ProductInstanceCloth productInstanceCloth) {
        this.productInstanceId = productInstanceCloth.getId();
        this.title = productInstanceCloth.getProduct().getTitle();
        this.linkOfPicture = productInstanceCloth.getLinkOfMainPicture();
        this.price = productInstanceCloth.getProduct().getPrice();
    }
}
