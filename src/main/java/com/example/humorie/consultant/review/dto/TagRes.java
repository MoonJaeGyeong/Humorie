package com.example.humorie.consultant.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TagRes {

    private Long tagId;

    private String tagName;

    private String tagContent;

}

