package com.thatsnotrlght.interview_radar;

import org.springframework.stereotype.Service;

@Service
public class InterviewService {
	private PaymentService paymentService;
	
	public InterviewService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	
	public void placeOrder() {
		paymentService.processPayment(10);
	}

	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

}
