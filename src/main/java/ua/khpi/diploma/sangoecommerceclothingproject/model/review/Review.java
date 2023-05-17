package ua.khpi.diploma.sangoecommerceclothingproject.model.review;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductCloth;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

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

    @Column(name = "title", nullable = false)
    private String titleOfReviewProduct;

    @Column(name = "picture", nullable = false, columnDefinition = "TEXT")
    private String picture;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product_id")
    private ProductCloth product;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;

    @JoinColumn(name = "rate")
    private Integer rate;

    @JoinColumn(name = "comment", nullable = false, columnDefinition = "TEXT")
    private String comment;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(
            name = "updated",
            nullable = false,
            columnDefinition = "DATE"
    )
    private LocalDate updated;

    public Review(ProductCloth product, User user, Integer rate, String comment) {
        this.id = UUID.randomUUID().toString();
        this.product = product;
        this.user = user;
        this.rate = rate;
        this.comment = comment;
    }
}
