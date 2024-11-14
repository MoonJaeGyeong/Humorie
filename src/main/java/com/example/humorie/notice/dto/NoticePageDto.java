package com.example.humorie.notice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class NoticePageDto {
    private List<NoticeListDto> notices;  // 현재 페이지에 있는 DTO 목록
    private int pageNumber;                 // 현재 페이지 번호
    private int pageSize;                   // 한 페이지에 표시되는 항목 수
    private long totalElements;             // 전체 항목 수
    private int totalPages;                 // 총 페이지 수

    public NoticePageDto(Page<NoticeListDto> noticePage) {
        this.notices = noticePage.getContent(); // 현재 페이지에 있는 데이터 목록
        this.pageNumber = noticePage.getNumber(); // 현재 페이지 번호
        this.pageSize = noticePage.getSize(); // 한 페이지에 표시되는 항목 수
        this.totalElements = noticePage.getTotalElements(); // 전체 항목 수
        this.totalPages = noticePage.getTotalPages(); // 총 페이지 수
    }

    public static NoticePageDto from(Page<NoticeListDto> noticePage) {
        return new NoticePageDto(noticePage);
    }
}
