package com.thatsnotrlght.interview_radar;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import com.thatsnotrlght.interview_radar.model.Company;
import com.thatsnotrlght.interview_radar.repository.CompanyRepository;

@SpringBootApplication
public class InterviewRadarApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(InterviewRadarApplication.class, args);
		var interviewService = context.getBean(InterviewService.class);
		interviewService.placeOrder();
//		var payPal = new InterviewService(new PayPalPaymentService());
//		payPal.placeOrder();
//		
//		var stripe = new InterviewService(new StripePaymentService());
//		stripe.placeOrder();
	}
	
	@Bean
	CommandLineRunner initDatabase(CompanyRepository repository) {
	    return args -> {
	        // Create a test company
	        Company company = new Company();
	        company.setName("Google");
	        company.setWebsiteUrl("https://google.com");
	        
	        // Save to the Docker database
	        repository.save(company);
	        
	        System.out.println("Test data saved! Check your Docker terminal.");
	    };
	}
	

}
