package com.thatsnotrlght.interview_radar.dto;

import java.time.LocalDate;

public record CreateReviewRequest(
		Integer rating,
		String interviewExperience,
		String feedback,
		LocalDate applicationDate,
		LocalDate lastContactDate,
		Boolean isGhosted
) {}
