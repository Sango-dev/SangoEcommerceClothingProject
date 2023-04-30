package ua.khpi.diploma.sangoecommerceclothingproject.service;

import ua.khpi.diploma.sangoecommerceclothingproject.dto.ReviewDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductCloth;
import ua.khpi.diploma.sangoecommerceclothingproject.model.review.Review;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.User;

import java.util.List;

public interface ReviewService {
   Review getReviewByProductAndUser(ProductCloth product, User user);
   void saveReviewByProductAndUser(ProductCloth product, User user);
   List<ReviewDto> getReviewsByUser(String username);
   ReviewDto findReviewById(String reviewId);
   void saveReview(String reviewId, Integer rate, String comment);
}
