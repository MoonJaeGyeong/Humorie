package com.example.humorie.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoticeSummaryDto {
    private Long id;           // 공지사항 ID
    private String title;      // 공지사항 제목
}
