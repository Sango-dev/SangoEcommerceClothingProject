package ua.khpi.diploma.sangoecommerceclothingproject.service;

import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductCloth;
import ua.khpi.diploma.sangoecommerceclothingproject.model.review.Review;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.User;

public interface ReviewService {
   Review getReviewByProductAndUser(ProductCloth product, User user);
   void saveReviewByProductAndUser(ProductCloth product, User user);
}
