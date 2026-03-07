package com.thatsnotrlght.interview_radar;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.thatsnotrlght.interview_radar.model.Company;
import com.thatsnotrlght.interview_radar.model.Review;
import com.thatsnotrlght.interview_radar.repository.CompanyRepository;
import com.thatsnotrlght.interview_radar.repository.ReviewRepository;
import com.thatsnotrlght.interview_radar.service.GhostService;

@SpringBootApplication
public class InterviewRadarApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterviewRadarApplication.class, args);
	}
	
	@Bean
	CommandLineRunner initDatabase(CompanyRepository companyRepo, ReviewRepository reviewRepo, GhostService ghostService) {
	    return args -> {
	        // 1. Ensure Google exists
	        Company google = companyRepo.findByNameIgnoreCase("Google")
	                .orElseGet(() -> {
	                    Company c = new Company();
	                    c.setName("Google");
	                    c.setWebsiteUrl("https://google.com");
	                    return companyRepo.save(c);
	                });
	        
	        System.out.println("Test data saved! Check your Docker terminal.");
	    	    
	        Review ghostReview = new Review();
	        ghostReview.setCompany(google);
	        ghostReview.setApplicationDate(LocalDate.now().minusDays(30)); // 30 days ago
	        ghostReview.setLastContactDate(LocalDate.now().minusDays(30)); // No contact since app
	        ghostReview.setFeedback("Applied via portal, no response yet.");
	        ghostReview.setGhosted(true); // Manually marking it for now
	        
	        reviewRepo.save(ghostReview);
	        System.out.println("Tracking Verified: Review saved with application date");
	        System.out.println(ghostService.calculateGhostProbability(google.getId()) * 100);
	    };
	    
	}
	

}
