package com.example.humorie.consultant.consult_detail.dto.response;

import com.example.humorie.consultant.consult_detail.entity.ConsultDetail;
import com.example.humorie.consultant.counselor.entity.Symptom;
import com.example.humorie.consultant.counselor.repository.SymptomRepository;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ConsultDetailListDto {
    private final Long id;
    private final Long counselorId;
    private final String counselorName; // 담당자명
    private final Boolean status; // 상담 상태
    private final List<String> symptoms; // 상담 영역
    private final Boolean isOnline; // 상담방법
    // 날짜 필드에 @JsonFormat 애너테이션 적용
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.M.d")
    private final LocalDate counselDate;
    private final LocalTime counselTIme;

    @Builder
    public ConsultDetailListDto(Long id, Long counselorId, String counselorName, Boolean status,
                                Boolean isOnline, LocalDate counselDate, LocalTime counselTIme, List<String> symptoms) {
        this.id = id;
        this.counselorId = counselorId;
        this.counselorName = counselorName;
        this.status = status;
        this.symptoms = symptoms; // 상담 영역
        this.isOnline = isOnline;
        this.counselDate = counselDate;
        this.counselTIme = counselTIme;
    }

    public static ConsultDetailListDto fromEntity(ConsultDetail consultDetail, SymptomRepository symptomRepository) {
        // 상담사의 증상 데이터를 리스트로 가져옴
        List<Symptom> symptoms = symptomRepository.findByCounselorId(consultDetail.getCounselor().getId());
        List<String> symptomNames = symptoms.stream()
                .map(Symptom::getSymptom) // Symptom 객체에서 symptom 필드만 추출
                .collect(Collectors.toList());

        return ConsultDetailListDto.builder()
                .id(consultDetail.getId())
                .counselorId(consultDetail.getCounselorId())
                .counselorName(consultDetail.getCounselorName())
                .status(consultDetail.getStatus())
                .symptoms(symptomNames)// 상담 영역
                .isOnline(consultDetail.getIsOnline())
                .counselDate(consultDetail.getCounselDate())
                .counselTIme(consultDetail.getCounselTime())
                .build();
    }
}
