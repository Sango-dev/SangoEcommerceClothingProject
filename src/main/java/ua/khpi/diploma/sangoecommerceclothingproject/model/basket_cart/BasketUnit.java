package ua.khpi.diploma.sangoecommerceclothingproject.model.basket_cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductInstanceCloth;

import javax.persistence.*;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "basket_units")
public class BasketUnit {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(
            name = "id",
            updatable = false
    )
    private String id;

    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductInstanceCloth product;

    @Column(name = "size")
    private String size;

    @Column(name = "quantity")
    private Long quantity;

    public BasketUnit(Basket basket, ProductInstanceCloth product, String size, Long quantity) {
        id = UUID.randomUUID().toString();
        this.basket = basket;
        this.product = product;
        this.size = size;
        this.quantity = quantity;
    }
}