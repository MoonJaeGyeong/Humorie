package com.example.humorie.consultant.counselor.repository;

import com.example.humorie.consultant.counselor.entity.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long> {

    List<Career> findByCounselorId(long counselorId);

}
