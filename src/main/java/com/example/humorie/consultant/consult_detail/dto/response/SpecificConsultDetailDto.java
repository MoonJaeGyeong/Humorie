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
public class SpecificConsultDetailDto {
    private final Long id;
    private final Long counselorId; // 상담사 식별자
    private final String counselorName; // 상담사 이름
    private final List<String> symptoms; // 상담 영역
    private final Boolean isOnline; // 온오프라인
    private final String location; // 지역
    private final Boolean status; // 상태
    private final String title; // 상담 제목
    private final String symptomCategory; // 상담 증상
    private final String symptomDetail; // 상담 증상에 대한 설명
    private final String content; // 상담 내용
    // 날짜 필드에 @JsonFormat 애너테이션 적용
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.M.d")
    private final LocalDate counselDate; // 상담 날짜
    private final LocalTime counselTime; // 상담 시간

    @Builder
    public SpecificConsultDetailDto(Long id, Long counselorId, String counselorName, List<String> symptoms, Boolean isOnline,
                                    Boolean status, String title, String symptomCategory, String symptomDetail, String content, LocalDate counselDate, LocalTime counselTime, String location) {  // Boolean 타입으로 설정
        this.id = id;
        this.counselorId = counselorId;
        this.counselorName = counselorName;
        this.symptoms = symptoms;
        this.isOnline = isOnline;
        this.location = location;
        this.status = status;
        this.title = title;
        this.symptomCategory = symptomCategory;
        this.symptomDetail = symptomDetail;
        this.content = content;
        this.counselDate = counselDate;
        this.counselTime = counselTime;
    }

    // ConsultDetail 엔티티에서 DTO로 변환하는 메서드
    public static SpecificConsultDetailDto fromEntity(ConsultDetail consultDetail, SymptomRepository symptomRepository) {
        // 상담사의 증상 데이터를 리스트로 가져옴
        List<Symptom> symptoms = symptomRepository.findByCounselorId(consultDetail.getCounselor().getId());
        List<String> symptomNames = symptoms.stream()
                .map(Symptom::getSymptom) // Symptom 객체에서 symptom 필드만 추출
                .collect(Collectors.toList());

        return SpecificConsultDetailDto.builder()
                .id(consultDetail.getId())
                .counselorId(consultDetail.getCounselor().getId())
                .counselorName(consultDetail.getCounselor().getName())
                .symptoms(symptomNames)// 상담 영역
                .isOnline(consultDetail.getIsOnline())
                .location(consultDetail.getLocation())
                .status(consultDetail.getStatus())
                .title(consultDetail.getTitle())
                .symptomCategory(consultDetail.getSymptomCategory())
                .symptomDetail(consultDetail.getSymptomDetail())
                .content(consultDetail.getContent())
                .counselDate(consultDetail.getCounselDate())
                .counselTime(consultDetail.getCounselTime())
                .build();
    }
}
