package com.example.humorie.consultant.counselor.dto;

import com.example.humorie.consultant.review.dto.ReviewDto;
import lombok.*;

import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CounselorProfileDto {

    private long counselorId;

    private String name;

    private String phoneNumber;

    private String email;

    private String qualification;

    private double rating;

    private int counselingCount;

    private int reviewCount;

    private String introduction;

    private Set<String> affiliations;

    private List<String> educations;

    private List<String> careers;

    private Set<String> counselingFields;

    private List<ReviewDto> reviews;

}
