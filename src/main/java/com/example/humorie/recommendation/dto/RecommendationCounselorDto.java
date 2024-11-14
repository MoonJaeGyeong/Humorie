package com.example.humorie.recommendation.dto;

import lombok.Getter;

import java.util.Set;

@Getter
public class RecommendationCounselorDto {

    private final long id;

    private final String name;

    private final Set<String> symptoms;

    private final double rating;

    private final int reviewCount;

    public RecommendationCounselorDto(long id, String name, Set<String> symptoms,
                                      double rating, int reviewCount){
        this.id = id;
        this.name = name;
        this.symptoms = symptoms;
        this.rating = rating;
        this.reviewCount = reviewCount;
    }

}
