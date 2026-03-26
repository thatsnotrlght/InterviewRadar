package com.thatsnotrlght.interview_radar;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.thatsnotrlght.interview_radar.model.Company;
import com.thatsnotrlght.interview_radar.model.Review;
import com.thatsnotrlght.interview_radar.repository.CompanyRepository;
import com.thatsnotrlght.interview_radar.repository.ReviewRepository;

@SpringBootApplication
public class InterviewRadarApplication {

	public static void main(String[] args) {
		SpringApplication.run(InterviewRadarApplication.class, args);
	}
	
	@Bean
	CommandLineRunner initDatabase(CompanyRepository companyRepo, ReviewRepository reviewRepo) {
	    return args -> {
	        Company google = getOrCreateCompany(companyRepo, "Google", "https://google.com");
	        Company stripe = getOrCreateCompany(companyRepo, "Stripe", "https://stripe.com");
	        Company notion = getOrCreateCompany(companyRepo, "Notion", "https://notion.so");
	        Company amazon = getOrCreateCompany(companyRepo, "Amazon", "https://amazon.com");

	        if (reviewRepo.findByCompanyId(google.getId()).isEmpty()) {
	        	seedReviews(reviewRepo, google, List.of(
	        			new ReviewSeed(5, "Great recruiter communication and timeline clarity.", 7, false),
	        			new ReviewSeed(4, "Interviewers were friendly and gave useful feedback.", 5, false),
	        			new ReviewSeed(2, "Process stalled after onsite with no updates.", 28, true)
	        	));
	        }
	        if (reviewRepo.findByCompanyId(stripe.getId()).isEmpty()) {
	        	seedReviews(reviewRepo, stripe, List.of(
	        			new ReviewSeed(5, "Very responsive process and smooth scheduling.", 3, false),
	        			new ReviewSeed(4, "Strong interview experience and quick follow-up.", 4, false),
	        			new ReviewSeed(4, "Good communication throughout each stage.", 6, false)
	        	));
	        }
	        if (reviewRepo.findByCompanyId(notion.getId()).isEmpty()) {
	        	seedReviews(reviewRepo, notion, List.of(
	        			new ReviewSeed(3, "Interesting interviews but slower final response.", 11, false),
	        			new ReviewSeed(2, "No reply after take-home submission.", 21, true)
	        	));
	        }
	        if (reviewRepo.findByCompanyId(amazon.getId()).isEmpty()) {
	        	seedReviews(reviewRepo, amazon, List.of(
	        			new ReviewSeed(3, "Structured process, but long wait between rounds.", 15, false),
	        			new ReviewSeed(1, "Ghosted after recruiter screen.", 30, true),
	        			new ReviewSeed(4, "Received final decision quickly.", 6, false)
	        	));
	        }
	    };
	}

	private Company getOrCreateCompany(CompanyRepository companyRepo, String name, String websiteUrl) {
		return companyRepo.findByNameIgnoreCase(name)
				.orElseGet(() -> {
					Company company = new Company();
					company.setName(name);
					company.setWebsiteUrl(websiteUrl);
					return companyRepo.save(company);
				});
	}

	private void seedReviews(ReviewRepository reviewRepo, Company company, List<ReviewSeed> seeds) {
		for (ReviewSeed seed : seeds) {
			Review review = new Review();
			review.setCompany(company);
			review.setRating(seed.rating());
			review.setInterviewExperience("Application process");
			review.setFeedback(seed.feedback());
			review.setApplicationDate(LocalDate.now().minusDays(seed.daysToResponse() + 7));
			review.setLastContactDate(LocalDate.now().minusDays(7));
			review.setGhosted(seed.ghosted());
			reviewRepo.save(review);
		}
	}

	private record ReviewSeed(Integer rating, String feedback, int daysToResponse, boolean ghosted) {}
}
