package ua.khpi.diploma.sangoecommerceclothingproject.model.review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductCloth;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.User;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "reviews"
)
public class Review {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(
            name = "id",
            updatable = false
    )
    private String id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductCloth product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JoinColumn(name = "rate")
    private Integer rate;

    @JoinColumn(name = "comment")
    private String comment;
}
