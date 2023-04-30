package ua.khpi.diploma.sangoecommerceclothingproject.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.ReviewDto;
import ua.khpi.diploma.sangoecommerceclothingproject.model.review.Review;

import java.util.List;

@Mapper
public interface ReviewMapper {
	ReviewMapper MAPPER = Mappers.getMapper(ReviewMapper.class);

	Review toReview(ReviewDto dto);

	@InheritInverseConfiguration
	ReviewDto fromReview(Review review);

	List<Review> toReviewList(List<ReviewDto> dtos);

	List<ReviewDto> fromReviewList(List<Review> list);
}