package ua.khpi.diploma.sangoecommerceclothingproject.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "products",
        uniqueConstraints = {
                @UniqueConstraint(name = "products_product_code_unique", columnNames = "product_code")
        }
)
public class ProductCloth {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(
            name = "id",
            updatable = false
    )
    private String id;




    @Column(
            name = "product_code",
            nullable = false

    )
    private String productCode;

    @Column(
            name = "title",
            nullable = false
    )
    private String title;

    @Column(
            name = "description",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String description;

    @Column(
            name = "composition",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String composition;

    @Column(
            name = "gender",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(
            name = "available",
            nullable = false
    )
    private Boolean available;

    @Column(
            name = "price",
            nullable = false
    )
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<ProductInstanceCloth> productInstances;
}
