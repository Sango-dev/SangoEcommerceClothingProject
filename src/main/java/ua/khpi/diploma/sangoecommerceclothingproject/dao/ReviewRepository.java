package ua.khpi.diploma.sangoecommerceclothingproject.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.ReviewDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductCloth;
import ua.khpi.diploma.sangoecommerceclothingproject.model.review.Review;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.User;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, String> {
    Review findByProductAndUser(ProductCloth product, User user);
    Review findReviewById(String id);
    List<Review> findReviewsByProduct(ProductCloth productCloth);
    void deleteAllByProduct(ProductCloth productCloth);
}
