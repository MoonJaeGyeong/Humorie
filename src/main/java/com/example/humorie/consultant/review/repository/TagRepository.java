package com.example.humorie.consultant.review.repository;

import com.example.humorie.account.entity.AccountDetail;
import com.example.humorie.consultant.review.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByTagNameAndAccount(String tagName, AccountDetail accountDetail);

    List<Tag> findByAccount(AccountDetail account);

    Optional<Tag> findByIdAndAccount(long tagId, AccountDetail account);

    boolean existsByTagNameAndAccount(String tagName, AccountDetail account);

}
