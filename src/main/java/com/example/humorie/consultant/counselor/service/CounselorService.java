package com.example.humorie.consultant.counselor.service;

import com.example.humorie.consultant.counselor.dto.CounselorProfileDto;
import com.example.humorie.consultant.counselor.entity.*;
import com.example.humorie.consultant.counselor.repository.*;
import com.example.humorie.consultant.review.dto.ReviewRes;
import com.example.humorie.consultant.review.entity.Review;
import com.example.humorie.consultant.review.repository.ReviewRepository;
import com.example.humorie.global.service.CommonService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CounselorService {

    private final ReviewRepository reviewRepository;
    private final SymptomRepository symptomRepository;
    private final EducationRepository educationRepository;
    private final AffiliationRepository affiliationRepository;
    private final CareerRepository careerRepository;
    private final CommonService commonService;

    @Transactional
    public CounselorProfileDto getCounselorProfile(long counselorId) {
        Counselor counselor = commonService.getCounselorById(counselorId);

        List<Review> reviews = reviewRepository.findByCounselorId(counselorId);

        List<ReviewRes> reviewDTOs = reviews.stream()
                .sorted(Comparator.comparingDouble(Review::getRating).reversed())
                .map(review -> ReviewRes.builder()
                        .title(review.getTitle())
                        .content(review.getContent())
                        .rating(review.getRating())
                        .createdAt(review.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        Set<String> affiliations = affiliationRepository.findByCounselorId(counselor.getId()).stream()
                .map(Affiliation::getSocietyName)
                .collect(Collectors.toSet());

        List<String> educations = educationRepository.findByCounselorId(counselor.getId()).stream()
                .map(Education::getContent)
                .collect(Collectors.toList());

        List<String> careers = careerRepository.findByCounselorId(counselor.getId()).stream()
                .map(Career::getContent)
                .collect(Collectors.toList());

        Set<String> symptoms = symptomRepository.findByCounselorId(counselor.getId()).stream()
                .map(Symptom::getSymptom)
                .collect(Collectors.toSet());

        return CounselorProfileDto.builder()
                .counselorId(counselor.getId())
                .name(counselor.getName())
                .phoneNumber(counselor.getPhoneNumber())
                .email(counselor.getEmail())
                .rating(counselor.getRating())
                .introduction(counselor.getIntroduction())
                .affiliations(affiliations)
                .educations(educations)
                .careers(careers)
                .counselingCount(counselor.getCounselingCount())
                .reviewCount(counselor.getReviewCount())
                .symptoms(symptoms)
                .reviews(reviewDTOs)
                .build();
    }

}