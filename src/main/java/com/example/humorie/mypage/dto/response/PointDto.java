package com.example.humorie.mypage.dto.response;

import com.example.humorie.account.entity.Point;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointDto {

    private Long pointId;

    private String title;

    private int earnedPoints;

    private int spentPoints;

    private LocalDateTime transactionDate;

    public static PointDto convertToPointDto(Point point) {
        int earnedPoint = point.getType().equals("earn") ? point.getPoints() : 0;
        int spentPoint = point.getType().equals("spend") ? point.getPoints() : 0;

        return PointDto.builder()
                .pointId(point.getId())
                .title(point.getTitle())
                .earnedPoints(earnedPoint)
                .spentPoints(spentPoint)
                .transactionDate(point.getTransactionDate())
                .build();
    }

}
