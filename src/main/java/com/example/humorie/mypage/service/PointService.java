package com.example.humorie.mypage.service;

import com.example.humorie.account.entity.AccountDetail;
import com.example.humorie.account.jwt.PrincipalDetails;
import com.example.humorie.global.exception.ErrorCode;
import com.example.humorie.global.exception.ErrorException;
import com.example.humorie.mypage.dto.response.PointDto;
import com.example.humorie.mypage.dto.response.TotalPointDto;
import com.example.humorie.account.entity.Point;
import com.example.humorie.mypage.repository.PointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;

    public List<PointDto> getAllPoints(PrincipalDetails principal) {
        if(principal == null){
            throw new ErrorException(ErrorCode.NONE_EXIST_USER);
        }

        AccountDetail account = principal.getAccountDetail();

        return pointRepository.findByAccount(account).stream()
                .sorted((t1, t2) -> t1.getTransactionDate().compareTo(t2.getTransactionDate()))
                .map(PointDto::convertToPointDto)
                .collect(Collectors.toList());
    }

    public List<PointDto> getEarnedPoints(PrincipalDetails principal) {
        if(principal == null){
            throw new ErrorException(ErrorCode.NONE_EXIST_USER);
        }

        AccountDetail account = principal.getAccountDetail();

        return pointRepository.findByAccountAndType(account, "earn").stream()
                .sorted((t1, t2) -> t1.getTransactionDate().compareTo(t2.getTransactionDate()))
                .map(PointDto::convertToPointDto)
                .collect(Collectors.toList());
    }

    public List<PointDto> getSpentPoints(PrincipalDetails principal) {
        if(principal == null){
            throw new ErrorException(ErrorCode.NONE_EXIST_USER);
        }

        AccountDetail account = principal.getAccountDetail();

        return pointRepository.findByAccountAndType(account, "spend").stream()
                .sorted((t1, t2) -> t1.getTransactionDate().compareTo(t2.getTransactionDate()))
                .map(PointDto::convertToPointDto)
                .collect(Collectors.toList());
    }

    public TotalPointDto getTotalPoints(PrincipalDetails principal) {
        if(principal == null){
            throw new ErrorException(ErrorCode.NONE_EXIST_USER);
        }

        AccountDetail account = principal.getAccountDetail();
        List<Point> points = pointRepository.findByAccount(account);

        int totalEarnedPoints = points.stream()
                .filter(t -> t.getType().equals("earn"))
                .mapToInt(Point::getPoints)
                .sum();

        int totalSpentPoints = points.stream()
                .filter(t -> t.getType().equals("spend"))
                .mapToInt(Point::getPoints)
                .sum();

        int totalPoints = totalEarnedPoints - totalSpentPoints;

        return TotalPointDto.builder().totalPoints(totalPoints).build();
    }

    public String deletePoints(PrincipalDetails principal, List<Long> pointIds) {
        if(principal == null){
            throw new ErrorException(ErrorCode.NONE_EXIST_USER);
        }

        AccountDetail account = principal.getAccountDetail();
        List<Point> points = pointRepository.findAllByAccountAndIdIn(account, pointIds);

        pointRepository.deleteAll(points);

        return "Success Delete";
    }

}
