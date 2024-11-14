package com.example.humorie.consultant.counselor.repository;

import com.example.humorie.consultant.counselor.entity.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SymptomRepository extends JpaRepository<Symptom, Long> {
    List<Symptom> findByCounselorId(long id);
}
