package com.thatsnotrlght.interview_radar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

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
	

}
