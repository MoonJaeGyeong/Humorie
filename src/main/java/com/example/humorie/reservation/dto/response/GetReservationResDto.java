package com.example.humorie.reservation.dto.response;

import lombok.Builder;

@Builder
public record GetReservationResDto( String ReservationUid,
                                    String counselorName,
                                    String buyerEmail,
                                    Integer finalPrice,
                                    String buyerName) { }
