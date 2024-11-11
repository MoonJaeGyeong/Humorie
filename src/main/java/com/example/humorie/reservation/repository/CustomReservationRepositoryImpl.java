package com.example.humorie.reservation.repository;

import com.example.humorie.reservation.entity.Reservation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import java.util.List;

import static com.example.humorie.account.entity.QAccountDetail.accountDetail;
import static com.example.humorie.consultant.counselor.entity.QCounselor.counselor;
import static com.example.humorie.reservation.entity.QReservation.reservation;

@RequiredArgsConstructor
public class CustomReservationRepositoryImpl implements CustomReservationRepository{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Reservation> findAllByAccountEmail(String email){
         return queryFactory.select(reservation)
                 .from(reservation)
                 .leftJoin(reservation.account, accountDetail)
                 .leftJoin(reservation.counselor, counselor)
                 .where(accountDetail.email.eq(email))
                 .orderBy(reservation.createdAt.desc())
                 .fetchJoin().fetch();
    }
}
