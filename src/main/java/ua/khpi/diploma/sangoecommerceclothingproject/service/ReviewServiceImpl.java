package ua.khpi.diploma.sangoecommerceclothingproject.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.ProductRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.ReviewRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.UserRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductCloth;
import ua.khpi.diploma.sangoecommerceclothingproject.model.review.Review;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.User;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    public Review getReviewByProductAndUser(ProductCloth product, User user) {
        return reviewRepository.findByProductAndUser(product, user);
    }

    @Override
    public void saveReviewByProductAndUser(ProductCloth product, User user) {
        Review review = new Review(product, user, 0, "");
        review.setProduct(product);
        review.setUser(user);

        Review savedReview = reviewRepository.save(review);
        product.getReviews().add(savedReview);
        productRepository.save(product);

        user.getReviews().add(savedReview);
        userRepository.save(user);
    }
}
