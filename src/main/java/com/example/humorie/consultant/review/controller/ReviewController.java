package com.example.humorie.consultant.review.controller;

import com.example.humorie.account.jwt.PrincipalDetails;
import com.example.humorie.consultant.review.dto.ReviewResList;
import com.example.humorie.consultant.review.service.ReviewService;
import com.example.humorie.consultant.review.dto.ReviewReq;
import com.example.humorie.global.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{consultId}")
    @Operation(summary = "리뷰 작성")
    public ErrorResponse<String> createReview(@RequestBody ReviewReq reviewReq,
                                              @PathVariable long consultId,
                                              @AuthenticationPrincipal PrincipalDetails principal) {
        return new ErrorResponse<>(reviewService.createReview(principal, consultId, reviewReq));
    }

    @PatchMapping("/{reviewId}")
    @Operation(summary = "리뷰 수정")
    public ErrorResponse<String> modifyReview(@RequestBody ReviewReq reviewReq,
                                              @PathVariable long reviewId,
                                              @AuthenticationPrincipal PrincipalDetails principal) {
        return new ErrorResponse<>(reviewService.modifyReview(principal, reviewId, reviewReq));
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "리뷰 삭제")
    public ErrorResponse<String> deleteReview(@PathVariable long reviewId,
                                              @AuthenticationPrincipal PrincipalDetails principal) {
        return new ErrorResponse<>(reviewService.deleteReview(principal, reviewId));
    }

    @GetMapping("/{counselorId}")
    @Operation(summary = "리뷰 리스트 조회")
    public ErrorResponse<ReviewResList> getReviewList(@PathVariable long counselorId) {
        return new ErrorResponse<>(reviewService.getReviewListByCounselor(counselorId));
    }

}
