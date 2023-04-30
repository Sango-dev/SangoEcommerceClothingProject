package ua.khpi.diploma.sangoecommerceclothingproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.ProductRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.ReviewRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dao.UserRepository;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.ReviewDto;
import ua.khpi.diploma.sangoecommerceclothingproject.mapper.ReviewMapper;
import ua.khpi.diploma.sangoecommerceclothingproject.model.product.ProductCloth;
import ua.khpi.diploma.sangoecommerceclothingproject.model.review.Review;
import ua.khpi.diploma.sangoecommerceclothingproject.model.user.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ReviewMapper reviewMapper = ReviewMapper.MAPPER;

    @Override
    public Review getReviewByProductAndUser(ProductCloth product, User user) {
        return reviewRepository.findByProductAndUser(product, user);
    }

    @Override
    public void saveReviewByProductAndUser(ProductCloth product, User user) {
        Review review = new Review(product, user, 0, "No comment");
        review.setProduct(product);
        review.setUser(user);
        review.setTitleOfReviewProduct(product.getTitle());
        product.getProductInstances()
                .stream()
                .limit(1)
                .forEach(pi -> review.setPicture(pi.getLinkOfMainPicture()));

        Review savedReview = reviewRepository.save(review);
        product.getReviews().add(savedReview);
        productRepository.save(product);

        user.getReviews().add(savedReview);
        userRepository.save(user);
    }

    @Override
    public List<ReviewDto> getReviewsByUser(String username) {
        User user = userRepository.findFirstByNickName(username);
        return reviewMapper.fromReviewList(user.getReviews());
    }

    @Override
    public ReviewDto findReviewById(String reviewId) {
        return reviewMapper.fromReview(reviewRepository.findReviewById(reviewId));
    }

    @Override
    public void saveReview(String reviewId, Integer rate, String comment) {
        Review review = reviewRepository.findReviewById(reviewId);
        review.setRate(rate);
        review.setComment(comment);
        reviewRepository.save(review);
    }
}
