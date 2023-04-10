package ua.khpi.diploma.sangoecommerceclothingproject.model.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(
            name = "created",
            nullable = false,
            columnDefinition = "DATE"
    )
    private LocalDate created;

    @UpdateTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(
            name = "updated",
            nullable = false,
            columnDefinition = "DATE"
    )
    private LocalDate updated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    private Double sum;
    private String address;
    private String phone;
    private String recipient;
    @Enumerated(EnumType.STRING)
    private PaymentOptions payment;
    @Enumerated(EnumType.STRING)
    private DeliveryOptions delivery;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String email;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetails> details;
}
