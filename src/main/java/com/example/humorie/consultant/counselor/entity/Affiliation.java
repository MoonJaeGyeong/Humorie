package com.example.humorie.consultant.counselor.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@AllArgsConstructor
@Table(name = "affiliation")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Affiliation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String societyName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Counselor counselor;

}

