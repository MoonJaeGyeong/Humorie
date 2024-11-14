package com.example.humorie.consultant.counselor.repository;

import com.example.humorie.consultant.counselor.entity.Affiliation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AffiliationRepository extends JpaRepository<Affiliation, Long> {

    List<Affiliation> findByCounselorId(long counselorId);

}
