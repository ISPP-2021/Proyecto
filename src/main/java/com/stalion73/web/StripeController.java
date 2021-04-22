package com.stalion73.web;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stalion73.http.PaymentIntentDto;
import com.stalion73.model.Supplier;
import com.stalion73.service.PaymentService;
import com.stalion73.service.SupplierService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.EventData;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.stripe.Stripe;
import java.util.Optional;
import com.stalion73.model.SubscriptionType;


import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/stripe")
@CrossOrigin(origins = "*")
public class StripeController {

    @Autowired
    private SupplierService supplierService;
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
                .setCancelUrl("https://bico-ds2.netlify.app")
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setClientReferenceId(usuario)
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

    @PostMapping("/subscription")
    public ResponseEntity<String> webhook(@RequestHeader("Stripe-Signature") String sigHeader,@RequestBody String body){
        String payload = body;
        String endpointSecret = "whsec_dxRQdnYqUrgppdF8qUQE4dga3u2bWxSJ";
        System.out.println(sigHeader);

        Event event = null;

        try {
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
            System.out.println(event);
        } catch (SignatureVerificationException e) {
            System.out.println(e);
            // Invalid signature
            // response.status(400);
            return null;
        }

        switch (event.getType()) {
            case "checkout.session.completed":
            
            
            EventDataObjectDeserializer deserializer = event.getDataObjectDeserializer();
            
            if(deserializer.getObject().isPresent()){
                Session session = (Session) event.getData().getObject();
                if(session.getAmountTotal()==999){
                    Supplier supplier = this.supplierService
                                            .findSupplierByUsername(session.getClientReferenceId()).get();
                     supplier.setSubscription(SubscriptionType.PREMIUM);
                     this.supplierService.save(supplier);
                    
                } else {
                    Supplier supplier = this.supplierService
                                            .findSupplierByUsername(session.getClientReferenceId()).get();
                     supplier.setSubscription(SubscriptionType.FREE);
                     this.supplierService.save(supplier);
                }

            }
            break;
            case "invoice.paid":
              // Continue to provision the subscription as payments continue to be made.
              // Store the status in your database and check when a user accesses your service.
              // This approach helps you avoid hitting rate limits.
              break;
            case "invoice.payment_failed":
              // The payment failed or the customer does not have a valid payment method.
              // The subscription becomes past_due. Notify your customer and send them to the
              // customer portal to update their payment information.
              break;
            default:
              // System.out.println("Unhandled event type: " + event.getType());
          }

        return null;
        
    }

}