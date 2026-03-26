package com.thatsnotrlght.interview_radar.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.thatsnotrlght.interview_radar.dto.CreateReviewRequest;
import com.thatsnotrlght.interview_radar.dto.ReviewDTO;
import com.thatsnotrlght.interview_radar.model.Company;
import com.thatsnotrlght.interview_radar.model.Review;
import com.thatsnotrlght.interview_radar.repository.CompanyRepository;
import com.thatsnotrlght.interview_radar.repository.ReviewRepository;

@RestController
@RequestMapping("/api/companies/{companyId}/reviews")
public class ReviewController {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@GetMapping
	public List<ReviewDTO> getReviewsForCompany(@PathVariable Long companyId) {
		ensureCompanyExists(companyId);
		return reviewRepository.findByCompanyId(companyId).stream()
				.map(this::toDto)
				.toList();
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ReviewDTO createReview(
			@PathVariable Long companyId,
			@RequestBody CreateReviewRequest request
	) {
		Company company = companyRepository.findById(companyId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found"));

		Review review = new Review();
		review.setCompany(company);
		review.setRating(request.rating());
		review.setInterviewExperience(request.interviewExperience());
		review.setFeedback(request.feedback());
		review.setApplicationDate(request.applicationDate());
		review.setLastContactDate(request.lastContactDate());
		review.setGhosted(Boolean.TRUE.equals(request.isGhosted()));

		Review saved = reviewRepository.save(review);
		return toDto(saved);
	}

	private void ensureCompanyExists(Long companyId) {
		if (!companyRepository.existsById(companyId)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found");
		}
	}

	private ReviewDTO toDto(Review review) {
		return new ReviewDTO(
				review.getId(),
				review.getRating(),
				review.getInterviewExperience(),
				review.getFeedback(),
				review.getApplicationDate(),
				review.getLastContactDate(),
				review.isGhosted(),
				review.getCreatedAt()
		);
	}
}
