package com.thatsnotrlght.interview_radar.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ReviewDTO(
		Long id,
		Integer rating,
		String interviewExperience,
		String feedback,
		LocalDate applicationDate,
		LocalDate lastContactDate,
		boolean isGhosted,
		LocalDateTime createdAt
) {}
