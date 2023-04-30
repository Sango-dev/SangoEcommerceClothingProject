package ua.khpi.diploma.sangoecommerceclothingproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.khpi.diploma.sangoecommerceclothingproject.dto.ReviewDto;
import ua.khpi.diploma.sangoecommerceclothingproject.service.ReviewService;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;

    @GetMapping
    public String listWithReviewsProducts(Principal principal, Model model) {
        List<ReviewDto> reviews = reviewService.getReviewsByUser(principal.getName());
        model.addAttribute("reviews", reviews);
        return "accessToReview";
    }

    @GetMapping("/{id}")
    public String listWithReviewsProducts(@PathVariable("id") String reviewId, Model model) {
        ReviewDto review = reviewService.findReviewById(reviewId);
        model.addAttribute("review", review);
        return "leaveReview";
    }

    @PostMapping("/leave-review")
    public String leaveReview(@RequestParam("rating") int rating,
                              @RequestParam("reviewId") String reviewId,
                              @RequestParam("comment") String comment) {

        reviewService.saveReview(reviewId, rating, comment);
        return "redirect:/review";
    }







}
