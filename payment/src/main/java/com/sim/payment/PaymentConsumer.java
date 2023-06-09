package com.sim.payment;

import java.util.function.Function;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConsumer {

	@Bean
	public Function<PaymentRequestEvent, PaymentCompleteEvent> paymentRequest() {
		return PaymentCompleteEvent::from;
	}

	@Bean
	Function<PaymentRefundEvent, PaymentRefundCompleteEvent> paymentRefundRequest() {
		return e -> {
			System.out.println(e.getId());
			return PaymentRefundCompleteEvent.from(e);
		};
	}
}
