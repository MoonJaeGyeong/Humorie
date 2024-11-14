package com.example.humorie.consultant.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewReq {

    private String title;

    private String content;

    private Double rating;

}
