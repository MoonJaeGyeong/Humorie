package com.example.humorie.reservation.repository;

import com.example.humorie.reservation.entity.Reservation;

import java.util.List;
import java.util.Optional;

public interface CustomReservationRepository {

    List<Reservation> findAllByAccountEmail(String email);

    Optional<Reservation> findByReservationUid(String Uid);
}
