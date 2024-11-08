package com.example.humorie.reservation.controller;

import com.example.humorie.account.jwt.PrincipalDetails;
import com.example.humorie.global.exception.ErrorResponse;
import com.example.humorie.reservation.dto.ReservationDto;
import com.example.humorie.reservation.dto.request.CreateReservationReq;
import com.example.humorie.reservation.dto.response.AvailableReservationDatesResDto;
import com.example.humorie.reservation.dto.response.AvailableReservationTimesResDto;
import com.example.humorie.reservation.dto.response.CreateReservationResDto;
import com.example.humorie.reservation.dto.response.GetReservationResDto;
import com.example.humorie.reservation.service.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Reservation" , description = "Reservation 관련 API 모음")
@RequiredArgsConstructor
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @Operation(summary = "상담 예약 생성")
    @PostMapping("/create")
    public ErrorResponse<CreateReservationResDto> createReservation(@AuthenticationPrincipal PrincipalDetails principal,
                                                                    @RequestBody @Valid CreateReservationReq createReservationReq){
        CreateReservationResDto response = reservationService.createReservation(principal, createReservationReq);

        return new ErrorResponse<>(response);
    }

    @Operation(summary = "상담 예약 전체 조회")
    @GetMapping("")
    public ErrorResponse<List<ReservationDto>> getReservations(@AuthenticationPrincipal PrincipalDetails principal){
        List<ReservationDto> response = reservationService.getReservations(principal);

        return new ErrorResponse<>(response);
    }

    @Operation(summary = "상담 예약 결제 전 조회")
    @GetMapping("/get")
    public ErrorResponse<GetReservationResDto> getReservation(@RequestParam(value = "reservationUid") String reservationUid){
        GetReservationResDto response = reservationService.getReservation(reservationUid);

        return new ErrorResponse<>(response);
    }


    @Operation(summary = "상담 가능 날짜")
    @GetMapping("/available/date/{counselorId}")
    public ErrorResponse<AvailableReservationDatesResDto> getAvailableReservationDate(@PathVariable(value = "counselorId") Long counselorId){
        AvailableReservationDatesResDto response =  reservationService.getAvailableReservationDate(counselorId);

        return new ErrorResponse<>(response);
    }

    @Operation(summary = "상담 가능 시간")
    @GetMapping("/available/time/{counselorId}")
    public ErrorResponse<AvailableReservationTimesResDto> getAvailableReservationTime(@PathVariable(value = "counselorId") Long counselorId,
                                                                                       @RequestParam(value = "selectDate") LocalDate selectDate){
      AvailableReservationTimesResDto response = reservationService.getAvailableReservationTime(counselorId, selectDate);

        return new ErrorResponse<>(response);
    }

}
