package ua.khpi.diploma.sangoecommerceclothingproject.model.basket_cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.User;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "baskets")
public class Basket {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(
            name = "id",
            updatable = false
    )
    private String id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "basket", cascade = CascadeType.ALL)
    private List<BasketUnit> units = new ArrayList<>();
}
