package com.example.humorie.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NoticeDetailWithNavigationDto {
    private NoticeDetailDto currentNotice;  // 현재 공지사항의 세부 정보
    private NoticeSummaryDto previousNotice; // 이전 공지사항의 요약 정보
    private NoticeSummaryDto nextNotice;     // 다음 공지사항의 요약 정보
}
