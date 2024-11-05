package com.example.humorie.reservation.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

public record CreateReservationReq(
        Long counselorId,
        String location,
        String counselContent,
        LocalDate counselDate,
        LocalTime counselTime,
        Integer point,
        Integer price,
        Integer finalPrice
) {
}
