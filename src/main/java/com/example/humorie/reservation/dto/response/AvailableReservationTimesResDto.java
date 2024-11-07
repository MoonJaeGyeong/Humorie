package com.example.humorie.reservation.dto.response;

import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record AvailableReservationTimesResDto( List<LocalTime> availableTimes) {
}
