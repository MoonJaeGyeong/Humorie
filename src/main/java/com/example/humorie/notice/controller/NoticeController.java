package com.example.humorie.notice.controller;

import com.example.humorie.notice.dto.NoticeDetailWithNavigationDto;
import com.example.humorie.notice.dto.NoticePageDto;
import com.example.humorie.notice.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @GetMapping("/get")
    @Operation(summary = "공지사항 전체 조회")
    public NoticePageDto getAllNotices(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return noticeService.getAllNotices(page, size);
    }

    @GetMapping("/search")
    @Operation(summary = "공지사항 검색")
    public NoticePageDto searchNotices(
            @RequestParam(required = false) String keyword,
            @RequestParam int page,
            @RequestParam int size
    ) {
        return noticeService.searchNotices(keyword, page, size);
    }

    @GetMapping("/{noticeId}")
    @Operation(summary = "특정 공지사항 및 이전/다음 공지사항 조회")
    public NoticeDetailWithNavigationDto getNoticeWithNavigation(@PathVariable Long noticeId) {
        return noticeService.getNoticeWithNavigation(noticeId);
    }
}
