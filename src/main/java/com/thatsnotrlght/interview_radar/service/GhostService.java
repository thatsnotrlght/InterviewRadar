package com.thatsnotrlght.interview_radar.service;

import com.thatsnotrlght.interview_radar.model.Review;
import com.thatsnotrlght.interview_radar.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GhostService {

    @Autowired
    private ReviewRepository reviewRepository;

    public double calculateGhostProbability(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        return calculateGhostProbability(reviews);
    }

    public double calculateGhostProbability(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return 0.0;
        }

        long ghostCount = 0;
        for (Review review : reviews) {
            if (isGhosted(review)) {
                ghostCount++;
            }
        }

        return (double) ghostCount / reviews.size();
    }

    public boolean isGhosted(Review review) {
        if (review.isGhosted()) {
            return true;
        }
        if (review.getLastContactDate() == null) {
            return false;
        }
        long daysSinceLastContact = ChronoUnit.DAYS.between(review.getLastContactDate(), LocalDate.now());
        return daysSinceLastContact > 14;
    }

    public double calculateAverageResponseDays(List<Review> reviews) {
        var responseDurations = reviews.stream()
                .filter(review -> !isGhosted(review))
                .filter(review -> review.getApplicationDate() != null && review.getLastContactDate() != null)
                .map(review -> ChronoUnit.DAYS.between(review.getApplicationDate(), review.getLastContactDate()))
                .filter(days -> days >= 0)
                .collect(Collectors.toList());
        if (responseDurations.isEmpty()) {
            return 0.0;
        }
        double total = responseDurations.stream().mapToLong(Long::longValue).sum();
        return total / responseDurations.size();
    }

    public double calculateResponseRatePercent(List<Review> reviews) {
        if (reviews.isEmpty()) {
            return 0.0;
        }
        long respondedCount = reviews.stream().filter(review -> !isGhosted(review)).count();
        return (respondedCount * 100.0) / reviews.size();
    }

    public double calculatePositiveRatePercent(List<Review> reviews) {
        var respondedReviews = reviews.stream()
                .filter(review -> !isGhosted(review))
                .filter(review -> review.getRating() != null)
                .toList();
        if (respondedReviews.isEmpty()) {
            return 0.0;
        }
        long positiveCount = respondedReviews.stream().filter(review -> review.getRating() >= 4).count();
        return (positiveCount * 100.0) / respondedReviews.size();
    }
}