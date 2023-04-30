package ua.khpi.diploma.sangoecommerceclothingproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.ProductInstanceRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.OrderDto;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.ShopCartDetailDto;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.ShopCartDto;
import ua.khpi.diploma.sangoecommerceclothingproject.mapper.OrderMapper;
import ua.khpi.diploma.sangoecommerceclothingproject.model.basket_cart.Basket;
import ua.khpi.diploma.sangoecommerceclothingproject.model.basket_cart.BasketUnit;
import ua.khpi.diploma.sangoecommerceclothingproject.model.basket_cart.ShopCart;
import ua.khpi.diploma.sangoecommerceclothingproject.model.order.Order;
import ua.khpi.diploma.sangoecommerceclothingproject.model.order.OrderDetails;
import ua.khpi.diploma.sangoecommerceclothingproject.model.order.OrderStatus;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductInstanceCloth;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.User;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShopCartServiceImpl implements ShopCartService {

    private final ShopCart shopCart;
    private final ProductInstanceRepository productInstanceRepository;
    private final UserService userService;
    //private final OrderService orderService;
    private final BasketService basketService;
    private final OrderMapper mapper = OrderMapper.MAPPER;

    @Override
    @Transactional
    public ShopCart attachToShopCart(String id, String size, String nickname) {
        final Optional<String> any = shopCart.getProducts().stream().filter(p -> p.getProduct().getId().equals(id) && p.getSize().equals(size)).
                map(p -> p.getId()).findFirst();

        if (any.isPresent()) {
            shopCart.getProducts().stream().filter(p -> p.getId().equals(any.get())).
                    forEach(p -> p.setQuantity(p.getQuantity() + 1));
        } else {

            ProductInstanceCloth productInstance = productInstanceRepository.findProductInstanceById(id);
            User user = userService.findFirstByNickName(nickname);
            Basket basket = user.getBasket();
            if (basket == null) {
                basket = basketService.createBasket(user);
                user.setBasket(basket);
                userService.save(user);
            }

            shopCart.getProducts().add(new BasketUnit(basket, productInstance, size, 1L));
        }

        return shopCart;
    }

    @Override
    public void flushShopCart() {
        shopCart.getProducts().clear();
    }

    @Override
    public ShopCartDto getCartDto() {
        ShopCartDto cartDto = new ShopCartDto();
        List<ShopCartDetailDto> list = new ArrayList<>(shopCart.getProducts().size());
        shopCart.getProducts().stream().forEach(basketUnit -> {
            ShopCartDetailDto dto = new ShopCartDetailDto(basketUnit.getProduct());
            dto.setSize(basketUnit.getSize());
            dto.setAmount(basketUnit.getQuantity());
            dto.setSum((dto.getPrice() * dto.getAmount()));
            list.add(dto);
        });

        cartDto.setCartDetails(list);
        cartDto.accumulate();

        return cartDto;
    }

    @Override
    public void storeShopCartToBasket(User user) {
        basketService.storeCartToBasket(user, shopCart);
    }

    @Override
    @Transactional
    public void loadCartFromBasket(User user) {
        List<BasketUnit> units = basketService.getAllFromBasket(user);
        if (units.size() != 0) {
            flushShopCart();
            shopCart.getProducts().addAll(units);
        }
    }

    @Override
    @Transactional
    public OrderDto commitCartToOrder(String nickname) {
        User user = userService.findFirstByNickName(nickname);
        Order order = new Order();
        order.setStatus(OrderStatus.NEW);

        List<BasketUnit> products = shopCart.getProducts();
        List<OrderDetails> orderDetails = products.stream()
                .map(basketUnit -> new OrderDetails(order, basketUnit.getProduct(), basketUnit.getQuantity(), basketUnit.getSize()))
                .collect(Collectors.toList());

        Double total = orderDetails.stream()
                .map(detail -> detail.getPrice() * detail.getAmount())
                .mapToDouble(Double::doubleValue).sum();

        total = Math.round(total * 100.0) / 100.0;

        order.setDetails(orderDetails);
        order.setSum(total);
        order.setAddress("none");
        order.setEmail(user.getEmail());
        order.setPhone(user.getPhone());
        order.setRecipient(user.getFirstName() + " " + user.getLastName());
        return mapper.fromOrder(order);
    }

    @Override
    public void removeProdFromShopCartByIdAndSize(String id, String size) {
       shopCart.getProducts().removeIf(bu -> bu.getProduct().getId().equals(id) && bu.getSize().equals(size));
    }

    @Override
    public void updateShopCartProductAmount(String id, String size, long amountDif) {
        BasketUnit basketUnit = shopCart.getProducts()
                .stream()
                .filter(bu -> bu.getProduct().getId().equals(id) && bu.getSize().equals(size))
                .findFirst()
                .get();


        basketUnit.setQuantity(basketUnit.getQuantity() + amountDif);
        if (basketUnit.getQuantity() == 0) {
            shopCart.getProducts().remove(basketUnit);
        }
    }
}




