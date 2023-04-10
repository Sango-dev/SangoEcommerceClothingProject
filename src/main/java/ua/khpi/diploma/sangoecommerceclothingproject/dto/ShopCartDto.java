package ua.khpi.diploma.sangoecommerceclothingproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShopCartDto {
    private Double sum;
    private List<ShopCartDetailDto> cartDetails = new ArrayList<>();

    public void accumulate() {
        sum = cartDetails.stream()
                .map(ShopCartDetailDto::getSum)
                .mapToDouble(Double::doubleValue)
                .sum();

        sum = Math.round(sum * 100.0) / 100.0;
    }
}
