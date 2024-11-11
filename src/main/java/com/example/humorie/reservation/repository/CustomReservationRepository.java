package com.example.humorie.reservation.repository;

import com.example.humorie.reservation.entity.Reservation;

import java.util.List;

public interface CustomReservationRepository {

    List<Reservation> findAllByAccountEmail(String email);
}
