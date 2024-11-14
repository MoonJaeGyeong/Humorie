package com.example.humorie.payment.service;

import com.example.humorie.account.jwt.PrincipalDetails;
import com.example.humorie.global.exception.ErrorCode;
import com.example.humorie.global.exception.ErrorException;
import com.example.humorie.account.entity.Point;
import com.example.humorie.mypage.repository.PointRepository;
import com.example.humorie.payment.dto.request.PaymentCallbackRequest;
import com.example.humorie.payment.dto.response.PaymentResDto;
import com.example.humorie.payment.entity.PaymentStatus;
import com.example.humorie.payment.repository.PaymentRepository;
import com.example.humorie.reservation.entity.Reservation;
import com.example.humorie.reservation.repository.ReservationRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.request.CancelData;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private static final Logger log = LoggerFactory.getLogger(PaymentService.class);
    private final ReservationRepository reservationRepository;
    private final PaymentRepository paymentRepository;
    private final IamportClient iamportClient;
    private final PointRepository pointRepository;

    public String paymentByCallback(PaymentCallbackRequest request) {

        try {
            // 결제 단건 조회(아임포트)
            IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(request.getPaymentUid());
            // 주문내역 조회
            Reservation reservation = reservationRepository.findReservationByReservationUid(request.getReservationUid())
                    .orElseThrow(() -> new ErrorException(ErrorCode.NONE_EXIST_RESERVATION));

            // DB에 저장된 결제 금액
            int price = reservation.getPayment().getFinalPrice();
            // 실 결제 금액
            int iamportPrice = iamportResponse.getResponse().getAmount().intValue();

            // 결제 완료가 아니면
            if(!iamportResponse.getResponse().getStatus().equals("paid")) {
                // 주문, 결제 삭제
                reservationRepository.delete(reservation);
                paymentRepository.delete(reservation.getPayment());

                throw new ErrorException(ErrorCode.INCOMPLETE_PAYMENT);
            }


            // 결제 금액 검증
            if(iamportPrice != price) {
                // 주문, 결제 삭제
                reservationRepository.delete(reservation);
                paymentRepository.delete(reservation.getPayment());
                // 결제금액 위변조로 의심되는 결제금액을 취소(아임포트)
                iamportClient.cancelPaymentByImpUid(new CancelData(iamportResponse.getResponse().getImpUid(), true, new BigDecimal(iamportPrice)));

                throw new ErrorException(ErrorCode.SUSPECTED_PAYMENT_FORGERY);
            }

            // 포인트 사용
            Point point = Point.builder()
                    .title("상담 예약")
                    .transactionDate(LocalDateTime.now())
                    .points(reservation.getPayment().getPoint())
                    .account(reservation.getAccount())
                    .type("spend")
                    .build();

            // 결제 상태 및 포인트 데이터베이스 반영
            reservation.getPayment().changePaymentBySuccess(PaymentStatus.OK, iamportResponse.getResponse().getImpUid());
            paymentRepository.save(reservation.getPayment());
            pointRepository.save(point);
            reservationRepository.save(reservation);

            return "Success payment";

        } catch (IamportResponseException | IOException e) {
            throw new ErrorException(ErrorCode.FAILED_PAYMENT);
        }
    }

    public List<PaymentResDto> getPayments(PrincipalDetails principal) {
        List<Reservation> reservations = reservationRepository.findAllByAccount_EmailOrderByCreatedAtDesc(principal.getUsername());

        List<PaymentResDto> paymentResDtos = new ArrayList<>();

        for (Reservation reservation : reservations) {
            try {
                if(reservation.getPayment().getPaymentUid() == null)
                    continue;

                IamportResponse<Payment> iamportResponse = iamportClient.paymentByImpUid(reservation.getPayment().getPaymentUid());
                Payment payment = iamportResponse.getResponse();
                String paymentMethod = payment.getPayMethod();
                String bankOrCardInfo = "";

                if (paymentMethod.equals("trans")) { // 계좌이체
                    bankOrCardInfo = payment.getBankName();
                } else if (paymentMethod.equals("card")) { // 카드
                    bankOrCardInfo = payment.getCardName();
                }
                PaymentResDto paymentResDto = PaymentResDto.builder()
                        .paymentMethod(bankOrCardInfo)
                        .paymentStatus(payment.getStatus())
                        .reservationUid(payment.getMerchantUid())
                        .approvedAt(payment.getPaidAt())
                        .price(payment.getAmount())
                        .build();
                paymentResDtos.add(paymentResDto);

            } catch (IamportResponseException | IOException e) {
                throw new ErrorException(ErrorCode.FAILED_PAYMENT);
            } catch (NullPointerException e){
                throw new ErrorException(ErrorCode.NONE_EXIST_RESERVATION);
            }
        }

        return paymentResDtos;
    }

    public Integer getTotalPrice(PrincipalDetails principal){
        Integer totalPrice = 0;

        List<Reservation> reservations = reservationRepository.findAllByAccount_EmailOrderByCreatedAtDesc(principal.getUsername());

        for(Reservation reservation : reservations){
            totalPrice += reservation.getPayment().getFinalPrice();
        }

        return totalPrice;
    }

    public String deletePayments(List<String> reservationUids){

        for(String reservationUid : reservationUids){
            Reservation reservation = reservationRepository.findReservationByReservationUid(reservationUid)
                    .orElseThrow(() -> new ErrorException(ErrorCode.NONE_EXIST_RESERVATION));

            com.example.humorie.payment.entity.Payment payment = reservation.getPayment();
            reservation.setPayment(null);

            paymentRepository.delete(payment);
        }

        return "Success Delete";
    }

}
