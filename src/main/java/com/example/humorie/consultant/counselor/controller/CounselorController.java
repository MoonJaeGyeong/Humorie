package com.example.humorie.consultant.counselor.controller;

import com.example.humorie.account.jwt.PrincipalDetails;
import com.example.humorie.consultant.counselor.dto.BookmarkDto;
import com.example.humorie.consultant.counselor.dto.CounselorProfileDto;
import com.example.humorie.consultant.counselor.service.BookmarkService;
import com.example.humorie.consultant.counselor.service.CounselorService;
import com.example.humorie.global.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/counselor")
public class CounselorController {

    private final CounselorService counselorService;
    private final BookmarkService bookmarkService;

    @GetMapping("/{counselorId}")
    @Operation(summary = "상담사 프로필 조회")
    public ErrorResponse<CounselorProfileDto> getCounselorProfile(@PathVariable long counselorId) {
        CounselorProfileDto counselorDTO = counselorService.getCounselorProfile(counselorId);

        return new ErrorResponse<>(counselorDTO);
    }

    @PostMapping("/bookmark/add")
    @Operation(summary = "상담사 북마크 추가")
    public ErrorResponse<String> addBookmark(@RequestParam long counselorId,
                                             @AuthenticationPrincipal PrincipalDetails principal) {
        return new ErrorResponse<>(bookmarkService.addBookmark(principal, counselorId));
    }

    @DeleteMapping("/bookmark/remove")
    @Operation(summary = "상담사 북마크 삭제")
    public ErrorResponse<String> removeBookmark(@RequestParam long counselorId,
                                                @AuthenticationPrincipal PrincipalDetails principal) {
        return new ErrorResponse<>(bookmarkService.removeBookmark(principal, counselorId));
    }

    @GetMapping("/bookmark")
    @Operation(summary = "북마크 리스트 조회")
    public ErrorResponse<List<BookmarkDto>> getUserBookmarks(@AuthenticationPrincipal PrincipalDetails principal) {
        return new ErrorResponse<>(bookmarkService.getAllBookmarks(principal));
    }

}
