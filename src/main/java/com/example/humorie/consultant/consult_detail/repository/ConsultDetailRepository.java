package com.example.humorie.consultant.consult_detail.repository;

import com.example.humorie.consultant.consult_detail.entity.ConsultDetail;
import com.example.humorie.account.entity.AccountDetail;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultDetailRepository extends JpaRepository<ConsultDetail, Long> {
    // 가장 최근에 받은 상담 조회
    @Query("SELECT c FROM ConsultDetail c WHERE c.account = :account ORDER BY c.counselDate DESC, c.counselTime DESC")
    List<ConsultDetail> findLatestConsultDetail(@Param("account") AccountDetail account, Pageable pageable);

    // 상담 내역 전체 조회
    @Query("SELECT c FROM ConsultDetail c WHERE c.account = :account ORDER BY c.counselDate DESC, c.counselTime DESC")
    Page<ConsultDetail> findAllConsultDetail(@Param("account") AccountDetail account, Pageable pageable);

    @Modifying
    @Query("UPDATE ConsultDetail c SET c.account = NULL WHERE c.account.id = :accountId")
    void detachAccountFromConsultDetail(@Param("accountId") Long accountId);

    @Modifying
    @Transactional
    void deleteByAccountId(Long accountId);
}