package com.example.humorie.notice.dto;

import com.example.humorie.notice.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDetailDto {
    private Long id;
    private String author;         // 작성자
    private int viewCount;         // 조회수
    private LocalDate createdDate; // 작성일
    private LocalTime createdTime; // 작성 시간
    private String title;          // 제목
    private String content;        // 내용

    // Notice 엔티티를 DTO로 변환하는 메서드
    public static NoticeDetailDto fromEntity(Notice notice) {
        return NoticeDetailDto.builder()
                .id(notice.getId())
                .author(notice.getAuthor())
                .viewCount(notice.getViewCount())
                .createdDate(notice.getCreatedDate())
                .createdTime(notice.getCreatedTime())
                .title(notice.getTitle())
                .content(notice.getContent())
                .build();
    }
}
