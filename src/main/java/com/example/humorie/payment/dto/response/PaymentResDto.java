package com.example.humorie.payment.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Builder
public class PaymentResDto {
    Date approvedAt;
    String reservationUid;
    String paymentMethod;
    String paymentStatus;
    BigDecimal price;

}
