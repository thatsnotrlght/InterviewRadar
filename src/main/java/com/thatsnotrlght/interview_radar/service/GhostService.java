package com.thatsnotrlght.interview_radar.service;

import com.thatsnotrlght.interview_radar.model.Review;
import com.thatsnotrlght.interview_radar.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class GhostService {

    @Autowired
    private ReviewRepository reviewRepository;

    public double calculateGhostProbability(Long companyId) {
        List<Review> reviews = reviewRepository.findByCompanyId(companyId);
        if (reviews.isEmpty()) return 0.0;

        long ghostCount = 0;
        for (Review review : reviews) {
            // Logic: If no contact for more than 14 days, it's a "ghost"
            long daysSinceLastContact = ChronoUnit.DAYS.between(review.getLastContactDate(), LocalDate.now());
            
            if (daysSinceLastContact > 14) {
                ghostCount++;
            }
        }

        // Return percentage (e.g., 0.75 for 75% ghosting rate)
        return (double) ghostCount / reviews.size();
    }
}