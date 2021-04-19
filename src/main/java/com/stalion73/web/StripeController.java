package com.stalion73.web;

import com.stalion73.http.PaymentIntentDto;
import com.stalion73.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.stripe.Stripe;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/stripe")
@CrossOrigin(origins = "*")
public class StripeController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/paymentintent")
    public ResponseEntity<String> payment(@RequestBody PaymentIntentDto paymentIntentDto) throws StripeException {
        PaymentIntent paymentIntent = paymentService.paymentIntent(paymentIntentDto);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
    }

    @PostMapping("/confirm/{id}")
    public ResponseEntity<String> confirm(@PathVariable("id") String id) throws StripeException {
        PaymentIntent paymentIntent = paymentService.confirm(id);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
    }

    @PostMapping("/cancel/{id}")
    public ResponseEntity<String> cancel(@PathVariable("id") String id) throws StripeException {
        PaymentIntent paymentIntent = paymentService.cancel(id);
        String paymentStr = paymentIntent.toJson();
        return new ResponseEntity<String>(paymentStr, HttpStatus.OK);
    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<Map<String, Object>> checkout(@RequestBody String price_id){
        Stripe.apiKey="sk_test_51IeGm1A32JKQZm0ztexmoajNRqjPuSxawtIfVhuBY2iu6AcCFAd1ilkWRSg6rUNFyTZ2TPcJSQATc8aC8gep7FW600iew5bfrA";
        String usuario = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(usuario);
        HttpHeaders headers = new HttpHeaders();
        SessionCreateParams params = new SessionCreateParams.Builder()
                .setSuccessUrl("https://bico-ds2.netlify.app")
                .setCancelUrl("https://bico-despliegue2.netlify.app")
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setCustomer(usuario)
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION)
                .addLineItem(new SessionCreateParams.LineItem.Builder().setQuantity(1L).setPrice(price_id).build())
                .build();
        try {
            Session session = Session.create(params);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("sessionId", session.getId());
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(responseData);//ResponseEntity<Map<String, Object>>(responseData, headers, HttpStatus.OK);
        }catch (Exception e){
            Map<String, Object> messageData = new HashMap<>();
            messageData.put("message", e.getMessage());
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("error", messageData);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(responseData);
        }
    }

    // @PostMapping("subscription")
    // public ResponseEntity<> webhook

}