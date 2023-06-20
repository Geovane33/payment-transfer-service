package com.purebank.paymenttransferservice.payment.service;


public interface PaymentService {
    void pay(Long walletId,  String paymentIdentifier);

}
