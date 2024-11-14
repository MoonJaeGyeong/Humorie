package com.example.humorie.mypage.controller;

import com.example.humorie.account.jwt.PrincipalDetails;
import com.example.humorie.global.exception.ErrorResponse;
import com.example.humorie.mypage.dto.request.PointIdListDto;
import com.example.humorie.mypage.dto.response.PointDto;
import com.example.humorie.mypage.dto.response.TotalPointDto;
import com.example.humorie.mypage.service.PointService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/points")
public class PointController {

    private final PointService pointService;

    @GetMapping("/all")
    @Operation(summary = "전체 포인트 내역 조회")
    public ErrorResponse<List<PointDto>> getAllPoints(@AuthenticationPrincipal PrincipalDetails principal) {
        List<PointDto> pointDto = pointService.getAllPoints(principal);
        return new ErrorResponse<>(pointDto);
    }

    @GetMapping("/earned")
    @Operation(summary = "적립 포인트 내역 조회")
    public ErrorResponse<List<PointDto>> getEarnedPoints(@AuthenticationPrincipal PrincipalDetails principal) {
        List<PointDto> pointDto = pointService.getEarnedPoints(principal);
        return new ErrorResponse<>(pointDto);
    }

    @GetMapping("/spent")
    @Operation(summary = "사용 포인트 내역 조회")
    public ErrorResponse<List<PointDto>> getSpentPoints(@AuthenticationPrincipal PrincipalDetails principal) {
        List<PointDto> pointDto = pointService.getSpentPoints(principal);
        return new ErrorResponse<>(pointDto);
    }

    @GetMapping("/total")
    @Operation(summary = "보유 포인트 조회")
    public ErrorResponse<TotalPointDto> getTotalPoints(@AuthenticationPrincipal PrincipalDetails principal) {
        TotalPointDto totalPoints = pointService.getTotalPoints(principal);
        return new ErrorResponse<>(totalPoints);
    }

    @DeleteMapping
    @Operation(summary = "포인트 내역 삭제")
    public ErrorResponse<String> deletePoints(@RequestBody PointIdListDto pointIdsDto,
                                              @AuthenticationPrincipal PrincipalDetails principal) {
        List<Long> pointIds = pointIdsDto.getPointIdList();
        return new ErrorResponse<>(pointService.deletePoints(principal, pointIds));
    }

}
