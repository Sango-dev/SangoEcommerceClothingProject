package ua.khpi.diploma.sangoecommerceclothingproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailsDto {
    private String productInstanceId;
    private String productInstanceTitle;
    private String linkOfMainPicture;
    private String size;
    private Long amount;
    private Double price;
}
