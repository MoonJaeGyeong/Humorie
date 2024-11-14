package com.example.humorie.consultant.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SymptoDto {

    private String categoryType;

    private String issueAreaType;

    private String symptom;

}
