package com.example.humorie.consultant.review.entity;

import com.example.humorie.account.entity.AccountDetail;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "tag")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tagName;

    private String tagContent;

    @ManyToOne(fetch = FetchType.LAZY)
    private AccountDetail account;

}
