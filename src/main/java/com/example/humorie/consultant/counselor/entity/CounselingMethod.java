package com.example.humorie.consultant.counselor.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "counseling_method")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CounselingMethod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String method;

    @ManyToOne(fetch = FetchType.LAZY)
    private Counselor counselor;

}
