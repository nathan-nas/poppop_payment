package com.example.payment;

import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {
    private static Gson gson = new Gson();

    static class CreatePaymentResponse {
        private String clientSecret;
        public CreatePaymentResponse(String clientSecret) {
            this.clientSecret = clientSecret;
        }
    }

    public String createPaymentIntent() throws StripeException {
        Stripe.apiKey = "sk_test_51OXKSzEP1gGhSTU9swReY7OefCOveH9MIyDZ5qhHaKfvPmpS9BO6T2AI3mhp4qYfR826L6ydbeXSRIYYJMc16Zom00oJuiIoj9";
        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount(Long.valueOf(19900))
                        .setCurrency("usd")
                        // In the latest version of the API, specifying the `automatic_payment_methods` parameter is optional because Stripe enables its functionality by default.
                        .setAutomaticPaymentMethods(
                                PaymentIntentCreateParams.AutomaticPaymentMethods
                                        .builder()
                                        .setEnabled(true)
                                        .build()
                        )
                        .build();

        PaymentIntent paymentIntent = PaymentIntent.create(params);

        CreatePaymentResponse paymentResponse = new CreatePaymentResponse(paymentIntent.getClientSecret());
        return gson.toJson(paymentResponse);
    }
}
