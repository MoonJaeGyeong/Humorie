package com.example.humorie.consultant.consult_detail.service;

import com.example.humorie.account.service.AccountService;
import com.example.humorie.consultant.consult_detail.dto.response.ConsultDetailPageDto;
import com.example.humorie.consultant.consult_detail.dto.response.LatestConsultDetailResDto;
import com.example.humorie.consultant.consult_detail.dto.response.ConsultDetailListDto;
import com.example.humorie.account.entity.AccountDetail;
import com.example.humorie.account.jwt.PrincipalDetails;
import com.example.humorie.consultant.consult_detail.dto.response.SpecificConsultDetailDto;
import com.example.humorie.consultant.consult_detail.entity.ConsultDetail;
import com.example.humorie.consultant.consult_detail.repository.ConsultDetailRepository;
import com.example.humorie.global.exception.ErrorCode;
import com.example.humorie.global.exception.ErrorException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConsultDetailService {
    private final AccountService accountService;
    private final ConsultDetailRepository consultDetailRepository;

    // 가장 최근에 받은 상담 조회
    public LatestConsultDetailResDto getLatestConsultDetailResponse(PrincipalDetails principalDetails) {
        AccountDetail accountDetail = principalDetails.getAccountDetail();
        if (accountDetail == null) {
            throw new ErrorException(ErrorCode.NONE_EXIST_USER);
        }
        log.info("Account ID: " + accountDetail.getId());

        // Pageable을 사용하여 결과를 하나로 제한
        List<ConsultDetail> consultDetails = consultDetailRepository.findLatestConsultDetail(accountDetail, PageRequest.of(0, 1));

        // 첫 번째 결과만 선택, 없으면 예외 발생
        ConsultDetail consultDetail = consultDetails.stream().findFirst().orElseThrow(() -> {
            log.info("No consult details found for account ID: {}", accountDetail.getId());
            return new ErrorException(ErrorCode.NO_RECENT_CONSULT_DETAIL);
        });

        return LatestConsultDetailResDto.fromEntity(consultDetail);
    }

    // 상담 내역 전체 조회
    public ConsultDetailPageDto findAllConsultDetail(PrincipalDetails principalDetails, Integer page, Integer size) {
        // PrincipalDetails 객체에서 AccountDetail 객체를 가져옴
        AccountDetail accountDetail = principalDetails.getAccountDetail();
        if (accountDetail == null) {
            throw new ErrorException(ErrorCode.NONE_EXIST_USER);
        }
        log.info("Account ID: " + accountDetail.getId());

        // 파라미터 값이 없을 경우 예외 처리
        if (page == null || size == null) {
            throw new ErrorException(ErrorCode.REQUEST_ERROR);  // 잘못된 요청 예외 발생
        }

        // 페이지 번호 유효성 검사
        if (page < 0) {
            throw new ErrorException(ErrorCode.NEGATIVE_PAGE_NUMBER);
        }

        // 페이지 크기 유효성 검사
        if (size < 1) {
            throw new ErrorException(ErrorCode.NEGATIVE_PAGE_SIZE);
        } else if (size > 9) {
            throw new ErrorException(ErrorCode.INVALID_PAGE_SIZE);
        }

        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size);

        // Page 객체를 가져옴
        Page<ConsultDetail> consultDetails = consultDetailRepository.findAllConsultDetail(accountDetail, pageable);

        // 총 페이지 수보다 요청된 페이지 번호가 클 경우 예외 처리
        if (pageable.getPageNumber() >= consultDetails.getTotalPages()) {
            log.error("Page number {} exceeds total pages {}", pageable.getPageNumber() + 1, consultDetails.getTotalPages());
            throw new ErrorException(ErrorCode.INVALID_PAGE_NUMBER);
        }

        // 페이지가 비어 있는지 확인
        if (consultDetails.isEmpty()) {
            throw new ErrorException(ErrorCode.NO_RECENT_CONSULT_DETAIL);
        }

        // Page 객체를 ConsultDetailListDto로 변환
        Page<ConsultDetailListDto> consultDetailListDtos = consultDetails.map(ConsultDetailListDto::fromEntity);
        log.info("Requested page number (0-based): {}", pageable.getPageNumber());
        log.info("Total pages available: {}", consultDetailListDtos.getTotalPages());

        // Page 정보를 포함한 ConsultDetailPageDto로 반환
        return new ConsultDetailPageDto(consultDetailListDtos);
    }

    // 특정 상담 내역 조회
    public SpecificConsultDetailDto getSpecificConsultDetail(Long id) {
        // Spring Data JPA가 기본적으로 제공하는 메서드
        // 주어진 id에 해당하는 엔티티를 데이터베이스에서 조회
        ConsultDetail consultDetail = consultDetailRepository.findById(id)
                .orElseThrow(() -> new ErrorException(ErrorCode.NONE_EXIST_CONSULT_DETAIL));

        // content가 존재하면 status를 true로 변경
        if (consultDetail.getContent() != null && !consultDetail.getContent().isEmpty()) {
            consultDetail.setStatus(true);
            consultDetailRepository.save(consultDetail); // 상태 업데이트 저장
        } else {
            // content가 없을 경우 예외 발생
            throw new ErrorException(ErrorCode.CONSULT_DETAIL_NOT_COMPLETED);
        }

        return SpecificConsultDetailDto.fromEntity(consultDetail);
    }

    @Transactional
    public void softDeleteConsultDetailsByAccountId(Long accountId) {
        List<ConsultDetail> consultDetails = consultDetailRepository.findByAccountId(accountId);
        for (ConsultDetail consultDetail : consultDetails) {
            consultDetail.setDeleted(true); // 소프트 삭제 처리
            consultDetailRepository.save(consultDetail);
        }
    }
    @Transactional
    public void detachAccountFromConsultDetail(Long accountId) {
        consultDetailRepository.detachAccountFromConsultDetail(accountId);
    }
}
