package com.thatsnotrlght.interview_radar.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reviews")
@Getter @Setter
@NoArgsConstructor
public class Review {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;
	
	private Integer rating; // 1-5 scale
    private String interviewExperience;
    private String feedback;

    // Ghost-O-Meter Data Points
    private LocalDate applicationDate;
    private LocalDate lastContactDate;
    private boolean isGhosted; 

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}