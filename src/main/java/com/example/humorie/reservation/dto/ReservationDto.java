package com.example.humorie.reservation.dto;


import com.example.humorie.reservation.entity.Reservation;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public record ReservationDto(
      Long reservationId,
      String counselorName,
      boolean isOnline,
      String location,
      LocalDate counselDate,
      LocalTime counselTime,
      LocalDateTime createdAt
) {

    public static ReservationDto from(Reservation reservation){
        return new ReservationDto(reservation.getId(), reservation.getCounselor().getName() ,reservation.getIsOnline(),
                reservation.getLocation(), reservation.getCounselDate(), reservation.getCounselTime(), reservation.getCreatedAt());

    }
}