package com.example.humorie.account.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "point")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int points;

    private String title;

    //earn or spend
    private String type;

    private LocalDateTime transactionDate;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountDetail account;

    public Point(AccountDetail accountDetail, int points, String title, String type) {
        this.account = accountDetail;
        this.points = points;
        this.title = title;
        this.type = type;
        this.transactionDate = LocalDateTime.now();
    }

    public Point build() {
        Point point = new Point();
        point.title = this.title;
        point.transactionDate = this.transactionDate;
        point.points = this.points;
        point.account = this.account;
        point.type = this.type;
        return point;
    }

}
