package com.example.humorie.consultant.consult_detail.dto.response;

import com.example.humorie.consultant.consult_detail.entity.ConsultDetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class LatestConsultDetailResDto {
    private final Long id;
    private final Long counselorId;
    private final String counselorName;
    private final double rating;
    // 날짜 필드에 @JsonFormat 애너테이션 적용
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.M.d")
    private final LocalDate counselDate;
    private final String title;
    private final String content;

    @Builder
    public LatestConsultDetailResDto(Long id, Long counselorId, String counselorName, LocalDate counselDate,
                                     String title, String content, double rating) {
        this.id = id;
        this.counselorId = counselorId;
        this.counselorName = counselorName;
        this.rating = rating;
        this.counselDate = counselDate;
        this.title = title;
        this.content = content;
    }

    public static LatestConsultDetailResDto fromEntity(ConsultDetail consultDetail) {
        return LatestConsultDetailResDto.builder()
                .id(consultDetail.getId())
                .counselorId(consultDetail.getCounselorId())
                .counselorName(consultDetail.getCounselorName())
                .rating(consultDetail.getRating())
                .counselDate(consultDetail.getCounselDate())
                .title(consultDetail.getTitle())
                .content(consultDetail.getContent())
                .build();
    }
}
