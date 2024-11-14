package com.example.humorie.payment.controller;

import com.example.humorie.account.jwt.PrincipalDetails;
import com.example.humorie.global.exception.ErrorCode;
import com.example.humorie.global.exception.ErrorException;
import com.example.humorie.global.exception.ErrorResponse;
import com.example.humorie.payment.dto.request.PaymentCallbackRequest;
import com.example.humorie.payment.dto.response.PaymentResDto;
import com.example.humorie.payment.service.PaymentService;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
@Tag(name = "Payment" , description = "Payment 관련 API 모음")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "결제 검증")
    @PostMapping("")
    public ErrorResponse<String> validationPayment(@RequestBody PaymentCallbackRequest request) {
        String response = paymentService.paymentByCallback(request);

        return new ErrorResponse<>(response);
    }

    @Operation(summary = "결제 내역 조회")
    @GetMapping("/get")
    public ErrorResponse<List<PaymentResDto>> getPayments(@AuthenticationPrincipal PrincipalDetails principal) {
        List<PaymentResDto> payments = paymentService.getPayments(principal);

        return new ErrorResponse<>(payments);
    }

    @Operation(summary = "전체 결제 금액 조회")
    @GetMapping("/total")
    public ErrorResponse<Integer> getTotalPrice(@AuthenticationPrincipal PrincipalDetails principal){
        if(principal == null) throw new ErrorException(ErrorCode.NONE_EXIST_USER);
        Integer totalPrice = paymentService.getTotalPrice(principal);

        return new ErrorResponse<>(totalPrice);
    }

    @Operation(summary = "결제 내역 삭제")
    @DeleteMapping("/delete")
    public ErrorResponse<String> deletePayments(@RequestParam("reservationUids") List<String> reservationUids){
        String response = paymentService.deletePayments(reservationUids);

        return new ErrorResponse<>(response);
    }

}
