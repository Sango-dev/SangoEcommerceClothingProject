package ua.khpi.diploma.sangoecommerceclothingproject.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sizes")
public class SizeCloth {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(
            name = "size",
            nullable = false
    )
    private String size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_instance_id", nullable = false)
    private ProductInstanceCloth productInstance;
}