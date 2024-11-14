package com.example.humorie.consultant.search.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.util.Objects;

import java.util.Set;

@Slf4j
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CounselorDto {

    private Long counselorId;

    private String name;

    private String gender;

    private String region;

    private double rating;

    private int reviewCount;

    private String introduction;

    private Set<String> counselingMethods;

    private Set<String> symptoms;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CounselorDto)) return false;
        CounselorDto that = (CounselorDto) o;
        return counselorId.equals(that.counselorId); // ID를 기준으로 동일성 비교
    }

    @Override
    public int hashCode() {
        return Objects.hash(counselorId); // ID를 기반으로 해시코드 생성
    }

}
