package com.thatsnotrlght.interview_radar.dto;

public record CompanyDashboardDTO(
		Long id,
		String name,
		String websiteUrl,
		double ghostScore,
		int reviewCount,
		double ghostingRatePercent,
		double avgResponseDays,
		double responseRatePercent,
		double positiveRatePercent,
		String processLabel
) {}
