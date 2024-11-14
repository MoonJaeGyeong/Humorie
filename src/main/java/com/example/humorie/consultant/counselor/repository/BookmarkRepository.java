package com.example.humorie.consultant.counselor.repository;

import com.example.humorie.account.entity.AccountDetail;
import com.example.humorie.consultant.counselor.entity.Bookmark;
import com.example.humorie.consultant.counselor.entity.Counselor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    void deleteByAccountAndCounselor(AccountDetail account, Counselor counselor);

    List<Bookmark> findAllByAccount(AccountDetail account);

    boolean existsByAccountAndCounselor(AccountDetail account, Counselor counselor);

    Optional<Bookmark> findByAccountAndCounselor(AccountDetail account, Counselor counselor);

}
