package com.example.humorie.consultant.counselor.repository;

import com.example.humorie.consultant.counselor.entity.CounselingMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CounselingMethodRepository extends JpaRepository<CounselingMethod, Long> {

    List<CounselingMethod> findByCounselorId(long counselorId);

}
