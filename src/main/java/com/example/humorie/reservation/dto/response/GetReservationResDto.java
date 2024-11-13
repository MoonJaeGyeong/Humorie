package com.example.humorie.reservation.dto.response;

import com.example.humorie.reservation.entity.Reservation;
import lombok.Builder;

public record GetReservationResDto( String reservationUid,
                                    String counselorName,
                                    String buyerEmail,
                                    Integer finalPrice,
                                    String buyerName) {
    public static GetReservationResDto from(Reservation reservation){
        return new GetReservationResDto(reservation.getReservationUid(),
                reservation.getCounselor().getName(),
                reservation.getAccount().getEmail(),
                reservation.getPayment().getFinalPrice(),
                reservation.getAccount().getName());
    }
}
