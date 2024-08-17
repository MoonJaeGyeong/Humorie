package com.example.humorie.consultant.consult_detail.dto.response;

import com.example.humorie.consultant.consult_detail.entity.ConsultDetail;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
public class SpecificConsultDetailDto {
    private final Long id;
    private final String counselorName;
    private final String content;
    private final Boolean status;
    private final String symptom;
    private final String title;
    private final LocalDate counselDate;
    private final LocalTime counselTime;
    private final String location;

    @Builder
    public SpecificConsultDetailDto(Long id, String counselorName, String content, Boolean status, String symptom, String title, LocalDate counselDate, LocalTime counselTime, String location) {  // Boolean 타입으로 설정
        this.id = id;
        this.counselorName = counselorName;
        this.content = content;
        this.status = status;
        this.symptom = symptom;
        this.title = title;
        this.counselDate = counselDate;
        this.counselTime = counselTime;
        this.location = location;
    }

    public static SpecificConsultDetailDto fromEntity(ConsultDetail consultDetail) {
        return SpecificConsultDetailDto.builder()
                .id(consultDetail.getId())
                .counselorName(consultDetail.getCounselor().getName())
                .content(consultDetail.getContent())
                .status(consultDetail.getStatus())
                .symptom(consultDetail.getSymptom())
                .title(consultDetail.getTitle())
                .counselDate(consultDetail.getCounselDate())
                .counselTime(consultDetail.getCounselTime())
                .location(consultDetail.getReservation().getLocation())
                .build();
    }
}