package ua.khpi.diploma.sangoecommerceclothingproject.model.product;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

@Table(name = "products_instances")
public class ProductInstanceCloth {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(
            name = "id",
            updatable = false
    )
    private String id;

    @Column(
            name = "link_main_picture",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String linkOfMainPicture;

    @Column(
            name = "link_front_picture",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String linkOfFrontPicture;

    @Column(
            name = "link_back_picture",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String linkOfBackPicture;

    @Column(
            name = "available",
            nullable = false
    )
    private Boolean available;

    @Column(
            name = "color",
            nullable = false,
            columnDefinition = "TEXT"
    )
    @Enumerated(EnumType.STRING)
    private Color color;

    @Column(
            name = "color_definition",
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String colorDefinition;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(
            name = "date_created",
            nullable = false,
            columnDefinition = "DATE"
    )
    private LocalDate dateCreated;

    @OneToMany(mappedBy = "productInstance", cascade = CascadeType.ALL)
    private List<SizeCloth> sizes;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductCloth product;

}
