package com.example.humorie.consultant.counselor.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookmarkDto {

    private Long bookmarkId;

    private CounselorDto counselor;

    private LocalDateTime createdAt;

}
