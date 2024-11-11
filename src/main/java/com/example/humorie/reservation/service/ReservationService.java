package com.example.humorie.reservation.service;

import com.example.humorie.account.entity.AccountDetail;
import com.example.humorie.account.jwt.PrincipalDetails;
import com.example.humorie.consultant.counselor.entity.Counselor;
import com.example.humorie.consultant.counselor.repository.CounselorRepository;
import com.example.humorie.global.exception.ErrorCode;
import com.example.humorie.global.exception.ErrorException;
import com.example.humorie.account.entity.Point;
import com.example.humorie.mypage.repository.PointRepository;
import com.example.humorie.payment.entity.Payment;
import com.example.humorie.payment.entity.PaymentStatus;
import com.example.humorie.payment.repository.PaymentRepository;
import com.example.humorie.reservation.dto.ReservationDto;
import com.example.humorie.reservation.dto.request.CreateReservationReq;
import com.example.humorie.reservation.dto.response.AvailableReservationDatesResDto;
import com.example.humorie.reservation.dto.response.AvailableReservationTimesResDto;
import com.example.humorie.reservation.dto.response.CreateReservationResDto;
import com.example.humorie.reservation.dto.response.GetReservationResDto;
import com.example.humorie.reservation.entity.Reservation;
import com.example.humorie.reservation.repository.CustomReservationRepository;
import com.example.humorie.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final CounselorRepository counselorRepository;
    private final PaymentRepository paymentRepository;
    private final PointRepository pointRepository;

    private final static int MAX_DAILY_RESERVATIONS = 10;
    private final static int MAX_RESERVATION_DATE = 14; // 2주
    private final static int RESERVATION_START_TIME = 10;
    private final static int RESERVATION_END_TIME = 19;

    public CreateReservationResDto createReservation(PrincipalDetails principal, CreateReservationReq createReservationReq) {
        Counselor counselor = counselorRepository.findById(createReservationReq.counselorId())
                .orElseThrow(() -> new ErrorException(ErrorCode.NON_EXIST_COUNSELOR));

        AccountDetail account = principal.getAccountDetail();

        List<Point> points = pointRepository.findByAccount(account);
        int totalEarnedPoints = points.stream()
                .filter(t -> t.getType().equals("earn"))
                .mapToInt(Point::getPoints)
                .sum();

        int totalSpentPoints = points.stream()
                .filter(t -> t.getType().equals("spend"))
                .mapToInt(Point::getPoints)
                .sum();

        int totalPoints = totalEarnedPoints - totalSpentPoints;

        if(totalPoints < createReservationReq.point()){
            throw new ErrorException(ErrorCode.EXCEED_POINT);
        }

        // 임시 결제내역 생성
        Payment payment = Payment.builder()
                .price(createReservationReq.price())
                .point(createReservationReq.point())
                .finalPrice(createReservationReq.finalPrice())
                .status(PaymentStatus.READY)
                .build();

        paymentRepository.save(payment);

        Reservation reservation = createReservationReq.toEntity(account, counselor, payment);

        reservationRepository.save(reservation);

        return new CreateReservationResDto(reservation.getReservationUid());
    }

    @Transactional(readOnly = true)
    public List<ReservationDto> getReservations(String userName) {
        List<Reservation> reservations = reservationRepository.findAllByAccountEmail(userName);
        return reservations.stream()
                .map(ReservationDto::from)
                .collect(Collectors.toList());
    }

    public GetReservationResDto getReservation(String uid){

        Reservation reservation = reservationRepository.findReservationByReservationUid(uid)
                .orElseThrow(() -> new ErrorException(ErrorCode.NONE_EXIST_RESERVATION));

        return GetReservationResDto.builder()
                .ReservationUid(reservation.getReservationUid())
                .buyerEmail(reservation.getAccount().getEmail())
                .buyerName(reservation.getAccount().getName())
                .counselorName(reservation.getCounselor().getName())
                .finalPrice(reservation.getPayment().getFinalPrice())
                .build();
    }

    public AvailableReservationDatesResDto getAvailableReservationDate(Long counselorId){
        List<LocalDate> dateList = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        for (int i=0; i<MAX_RESERVATION_DATE; i++){
            dateList.add(currentDate.plusDays(i));
        }

        for (int i=0; i<dateList.size(); i++){
            LocalDate checkDate = dateList.get(i);
            int countReservation = reservationRepository.countByCounselorIdAndCounselDate(counselorId, checkDate);

            if(countReservation >= MAX_DAILY_RESERVATIONS ||
                    (checkDate.isEqual(currentDate) && currentTime.isAfter(LocalTime.of(RESERVATION_END_TIME,0)))){

                dateList.remove(i);
                i--;
            }
        }

        return new AvailableReservationDatesResDto(dateList);
    }

    public AvailableReservationTimesResDto getAvailableReservationTime(Long counselorId, LocalDate selectDate){
        List<LocalTime> timeList = new ArrayList<>();
        LocalTime currentTime = LocalTime.now();
        LocalDate currentDate = LocalDate.now();
        for (int hour = RESERVATION_START_TIME; hour <= RESERVATION_END_TIME; hour++) {
            timeList.add(LocalTime.of(hour, 0));
        }

        for (int i=0; i<timeList.size(); i++){
            LocalTime checkTime = timeList.get(i);
            boolean existReservation = reservationRepository.existsByCounselorIdAndCounselDateAndCounselTime(counselorId, selectDate, checkTime);
            if(existReservation || (selectDate.isEqual(currentDate)  && currentTime.isAfter(checkTime))){
                timeList.remove(i);
                i--;
            }
        }

        return new AvailableReservationTimesResDto(timeList);
    }

    @Transactional
    public void deleteReservationsByAccountId(Long accountId) {
        // Reservation에서 특정 accountId에 해당하는 모든 예약 삭제
        reservationRepository.deleteByAccountId(accountId);
    }

    @Transactional
    public void detachAccountFromReservation(Long accountId) {
        reservationRepository.detachAccountFromReservation(accountId);
    }

}


