package com.example.humorie.reservation.dto.response;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public record AvailableReservationDatesResDto(
        List<LocalDate> availableDates) { }
