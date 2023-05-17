package ua.khpi.diploma.sangoecommerceclothingproject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
   private String id;
   private String titleOfReviewProduct;
   private String picture;
   private Integer rate;
   private String comment;
   private LocalDate updated;
   private UserDto user;
}
