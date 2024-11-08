package com.example.humorie.reservation.dto.request;

import com.example.humorie.account.entity.AccountDetail;
import com.example.humorie.consultant.counselor.entity.Counselor;
import com.example.humorie.payment.entity.Payment;
import com.example.humorie.reservation.entity.Reservation;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationReq(
        Long counselorId,
        Boolean isOnline,
        String location,
        String counselContent,
        LocalDate counselDate,
        LocalTime counselTime,
        Integer point,
        Integer price,
        Integer finalPrice
) {
    public Reservation toEntity(AccountDetail account, Counselor counselor, Payment payment){
        return  Reservation.createReservation(account,counselor,payment, isOnline,location, counselContent, counselDate, counselTime);
    }
}
