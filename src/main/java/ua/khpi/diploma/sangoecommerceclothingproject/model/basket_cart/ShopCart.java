package ua.khpi.diploma.sangoecommerceclothingproject.model.basket_cart;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;

@Data
@SessionScope
@Component
public class ShopCart {
    private List<BasketUnit> products = new ArrayList<>();
}
