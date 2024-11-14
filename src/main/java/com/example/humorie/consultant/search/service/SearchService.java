package com.example.humorie.consultant.search.service;

import com.example.humorie.consultant.counselor.entity.CounselingMethod;
import com.example.humorie.consultant.counselor.entity.Counselor;
import com.example.humorie.consultant.counselor.entity.Symptom;
import com.example.humorie.consultant.counselor.repository.CounselingMethodRepository;
import com.example.humorie.consultant.counselor.repository.CounselorRepository;
import com.example.humorie.consultant.counselor.repository.SymptomRepository;
import com.example.humorie.consultant.search.dto.CounselorDto;
import com.example.humorie.consultant.search.dto.SearchReq;
import com.example.humorie.consultant.search.dto.SymptoDto;
import com.example.humorie.global.exception.ErrorCode;
import com.example.humorie.global.exception.ErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class SearchService {

    private final CounselorRepository counselorRepository;
    private final CounselingMethodRepository methodRepository;
    private final SymptomRepository symptomRepository;

    public List<CounselorDto> getAllCounselors() {
        List<Counselor> counselors = counselorRepository.findAll();

        return counselors.stream()
                .sorted(Comparator.comparingDouble(Counselor::getRating).reversed())
                .map(this::mapToCounselorDto)
                .collect(Collectors.toList());
    }


    private Specification<Counselor> buildCounselorSpecification(String counselingMethod, String gender, String region) {
        return Specification
                .where(CounselorSpecification.hasCounselingMethod(counselingMethod))
                .and(CounselorSpecification.hasGender(gender))
                .and(CounselorSpecification.hasRegion(region));
    }

    public List<CounselorDto> searchByKeywords(SearchReq searchReq, String counselingMethod, String gender, String region, String order) {
        try {
            Specification<Counselor> specification = buildCounselorSpecification(counselingMethod, gender, region)
                    .and(CounselorSpecification.orderBy(order));

            List<Counselor> counselorList = counselorRepository.findAll(specification);
            List<CounselorDto> result = new ArrayList<>();

            for (Counselor counselor : counselorList) {
                List<Symptom> counselorSymptoms = counselor.getSymptoms();

                boolean matchAllGroups = searchReq.getSymptoms().stream()
                        .allMatch(group -> matchesCategoryIssueAndSymptom(counselorSymptoms, group));

                if (matchAllGroups) {
                    CounselorDto counselorDto = mapToCounselorDto(counselor);
                    result.add(counselorDto);
                }
            }

            return result;
        } catch (Exception e) {
            throw new ErrorException(ErrorCode.SEARCH_FAILED);
        }
    }

    public List<CounselorDto> searchBySingleKeyword(String keyword, String counselingMethod, String gender, String region, String order) {
        try {
            Specification<Counselor> specification = buildCounselorSpecification(counselingMethod, gender, region)
                    .and(CounselorSpecification.orderBy(order));

            List<Counselor> counselorList = counselorRepository.findAll(specification);
            List<CounselorDto> result = new ArrayList<>();

            for (Counselor counselor : counselorList) {
                List<Symptom> counselorSymptoms = counselor.getSymptoms();

                if (containsKeywordInSymptoms(counselorSymptoms, keyword)) {
                    CounselorDto counselorDto = mapToCounselorDto(counselor);
                    result.add(counselorDto);
                }
            }

            return result;
        } catch (Exception e) {
            throw new ErrorException(ErrorCode.SEARCH_FAILED);
        }
    }

    public List<CounselorDto> searchByConditions(String counselingMethod, String gender, String region, String order) {
        Specification<Counselor> specification = buildCounselorSpecification(counselingMethod, gender, region)
                .and(CounselorSpecification.orderBy(order));

        List<Counselor> counselors = counselorRepository.findAll(specification);

        return counselors.stream()
                .map(this::mapToCounselorDto)
                .collect(Collectors.toList());
    }


    private boolean matchesCategoryIssueAndSymptom(List<Symptom> symptoms, SymptoDto symptoDto) {
        for (Symptom symptom : symptoms) {
            if (symptom.getCategoryType().equals(symptoDto.getCategoryType()) &&
                    symptom.getIssueAreaType().equals(symptoDto.getIssueAreaType()) &&
                    symptom.getSymptom().equals(symptoDto.getSymptom())) {
                return true;
            }
        }
        return false;
    }

    private boolean containsKeywordInSymptoms(List<Symptom> symptoms, String keyword) {
        for (Symptom symptom : symptoms) {
            if (symptom.getSymptom().contains(keyword)) {
                return true;
            }
        }
        return false;
    }


    private CounselorDto mapToCounselorDto(Counselor counselor) {
        Set<String> symptoms = symptomRepository.findByCounselorId(counselor.getId()).stream()
                .map(Symptom::getSymptom)
                .collect(Collectors.toSet());

        Set<String> counselingMethods = methodRepository.findByCounselorId(counselor.getId()).stream()
                .map(CounselingMethod::getMethod)
                .collect(Collectors.toSet());

        return new CounselorDto(
                counselor.getId(),
                counselor.getName(),
                counselor.getGender(),
                counselor.getRegion(),
                counselor.getRating(),
                counselor.getReviewCount(),
                counselor.getIntroduction(),
                counselingMethods,
                symptoms
        );

    }
}