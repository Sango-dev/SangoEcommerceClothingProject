package ua.khpi.diploma.sangoecommerceclothingproject.model.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductInstanceCloth;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders_details")
public class OrderDetails {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private String productInstanceId;
    private String productInstanceTitle;
    private String linkOfMainPicture;
    private String size;

    private Long amount;
    private Double price;

    public OrderDetails(Order order, ProductInstanceCloth productInstanceCloth, Long amount, String size) {
        this.order = order;
        this.productInstanceId = productInstanceCloth.getId();
        this.productInstanceTitle = productInstanceCloth.getProduct().getTitle();
        this.linkOfMainPicture = productInstanceCloth.getLinkOfMainPicture();
        this.size = size;
        this.amount = amount;
        this.price = productInstanceCloth.getProduct().getPrice();
    }
}
