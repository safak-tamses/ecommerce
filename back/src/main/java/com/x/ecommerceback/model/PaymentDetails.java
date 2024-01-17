package com.x.ecommerceback.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentDetails {
    private String paymentMethod; //todo
    private String status; //todo
    private String paymentId;
    private String razorpayPaymentLinkId;
    private String razorpayPaymentLinkReference;
    private String razorpayPaymentLinkStatus;
    private String razorpayPaymentId;
}
