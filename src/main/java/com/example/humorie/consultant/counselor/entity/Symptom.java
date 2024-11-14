package com.example.humorie.consultant.counselor.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "symptom")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Symptom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String categoryType;

    private String issueAreaType;

    private String symptom;

    @ManyToOne(fetch = FetchType.LAZY)
    private Counselor counselor;

}
