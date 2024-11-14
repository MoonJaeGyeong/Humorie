package com.example.humorie.notice.dto;

import com.example.humorie.notice.entity.Notice;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class NoticeListDto {
    private Long id;
    private String title;
    private LocalDate createdDate;
    private LocalTime createdTime;
    private int viewCount;

    // Notice 엔티티를 GetAllNoticeDto로 변환하는 메서드
    public static NoticeListDto fromEntity(Notice notice) {
        return NoticeListDto.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .createdDate(notice.getCreatedDate())
                .createdTime(notice.getCreatedTime())
                .viewCount(notice.getViewCount())
                .build();
    }
}
