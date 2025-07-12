package com.server.lifestyle.service;

import com.server.lifestyle.model.Product;
import com.server.lifestyle.model.Review;
import com.server.lifestyle.model.User;
import com.server.lifestyle.request.CreateReviewRequest;

import java.util.List;

public interface ReviewService {
    Review createReview(CreateReviewRequest req, User user, Product product);
    List<Review> getReviewByProductId(Long productId);
    Review updateReview(Long reviewId, String reviewText, double rating, Long userId) throws Exception;
    void deleteReview(Long reviewId, Long userId) throws Exception;
    Review getReviewById(Long reviewId) throws Exception;

}
