package com.example.humorie.consultant.counselor.entity;

import com.example.humorie.account.entity.AccountDetail;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "bookmark")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountDetail account;

    @ManyToOne(fetch = FetchType.LAZY)
    private Counselor counselor;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
