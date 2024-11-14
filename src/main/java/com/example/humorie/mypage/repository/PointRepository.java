package com.example.humorie.mypage.repository;

import com.example.humorie.account.entity.AccountDetail;
import com.example.humorie.account.entity.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Long> {

    List<Point> findByAccount(AccountDetail accountDetail);

    List<Point> findByAccountAndType(AccountDetail accountDetail, String type);

    List<Point> findAllByAccountAndIdIn(AccountDetail account, List<Long> pointIds);

}
