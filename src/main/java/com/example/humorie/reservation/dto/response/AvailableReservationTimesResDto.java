package com.example.humorie.reservation.dto.response;

import java.time.LocalTime;
import java.util.List;

public record AvailableReservationTimesResDto( List<LocalTime> availableTimes) {
}
