package com.example.humorie.notice.service;

import com.example.humorie.global.exception.ErrorCode;
import com.example.humorie.global.exception.ErrorException;
import com.example.humorie.notice.dto.*;
import com.example.humorie.notice.entity.Notice;
import com.example.humorie.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {
    private final NoticeRepository noticeRepository;

    public NoticePageDto getAllNotices(int page, int size) {
        // 페이지 번호에 대한 유효성 검사
        if (page < 0) {
            throw new ErrorException(ErrorCode.NEGATIVE_PAGE_NUMBER);
        }

        // 페이지 크기에 대한 유효성 검사
        if (size < 1) {
            throw new ErrorException(ErrorCode.NEGATIVE_PAGE_SIZE);
        }
        if (size > 8) {
            throw new ErrorException(ErrorCode.INVALID_PAGE_SIZE);
        }

        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size);

        // 공지사항 조회 결과 가져오기
        Page<Notice> noticePage = noticeRepository.findAllByOrderByCreatedDateDescCreatedTimeDesc(pageable);

        // 데이터가 없는 경우 빈 Page 반환
        if (noticePage.getTotalPages() == 0) {
            return NoticePageDto.from(Page.empty(pageable));
        }

        // 총 페이지 수보다 요청된 페이지 번호가 클 경우 예외 처리
        if (pageable.getPageNumber() >= noticePage.getTotalPages()) {
            log.error("Page number {} exceeds total pages {}", pageable.getPageNumber() + 1, noticePage.getTotalPages());
            throw new ErrorException(ErrorCode.INVALID_PAGE_NUMBER);
        }

        // 엔티티를 DTO로 변환하여 NoticePageDto로 반환
        Page<NoticeListDto> dtoPage = noticePage.map(NoticeListDto::fromEntity);
        return NoticePageDto.from(dtoPage);
    }

    public NoticePageDto getAllNotices(Pageable pageable) {
        Page<Notice> noticePage = noticeRepository.findAllByOrderByCreatedDateDescCreatedTimeDesc(pageable);

        Page<NoticeListDto> dtoPage = noticePage.map(NoticeListDto::fromEntity);
        return NoticePageDto.from(dtoPage);
    }

    public NoticePageDto searchNotices(String keyword, int page, int size) {
        // 페이지 번호에 대한 유효성 검사
        if (page < 0) {
            throw new ErrorException(ErrorCode.NEGATIVE_PAGE_NUMBER);
        }

        // 페이지 크기에 대한 유효성 검사
        if (size < 1) {
            throw new ErrorException(ErrorCode.NEGATIVE_PAGE_SIZE);
        } else if (size > 8) {
            throw new ErrorException(ErrorCode.INVALID_PAGE_SIZE);
        }

        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size);

        // 키워드가 없는 경우 전체 공지사항 반환
        if (keyword == null || keyword.trim().isEmpty()) {
            // 전체 공지사항 조회
            NoticePageDto allNotices = getAllNotices(pageable);

            // 총 페이지 수보다 요청된 페이지 번호가 클 경우 예외 처리
            if (page >= allNotices.getTotalPages()) {
                log.error("Page number {} exceeds total pages {}", page + 1, allNotices.getTotalPages());
                throw new ErrorException(ErrorCode.INVALID_PAGE_NUMBER);
            }

            return allNotices;
        }

        // 키워드 검색 결과 가져오기 (제목 또는 내용에서 검색)
        Page<Notice> searchResults = noticeRepository.findByTitleContainingOrContentContaining(keyword, pageable);

        // 검색 결과가 없을 경우 빈 페이지 반환
        if (searchResults.getTotalPages() == 0) {
            return NoticePageDto.from(Page.empty(pageable));
        }

        // 총 페이지 수보다 요청된 페이지 번호가 클 경우 예외 처리
        if(pageable.getPageNumber() >= searchResults.getTotalPages()) {
            log.error("Page number {} exceeds total pages {}", pageable.getPageNumber() + 1, searchResults.getTotalPages());
            throw new ErrorException(ErrorCode.INVALID_PAGE_NUMBER);
        }

        // 결과를 DTO로 변환
        Page<NoticeListDto> dtoPage = searchResults.map(NoticeListDto::fromEntity);

        return NoticePageDto.from(dtoPage);
    }

    public  NoticeDetailWithNavigationDto getNoticeWithNavigation(Long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new ErrorException(ErrorCode.NONE_EXIST_NOTICE));

        // 조회수 1 증가
        notice.setViewCount(notice.getViewCount() + 1);
        noticeRepository.save(notice); // 변경된 조회수를 데이터베이스에 저장

        // 공지사항 목록을 날짜와 시간 순으로 정렬하여 가져옴
        List<Notice> notices = noticeRepository.findAllByOrderByCreatedDateDescCreatedTimeDesc();

        // 현재 공지사항의 위치 파악
        int currentIndex = notices.indexOf(notice);

        // 이전 공지사항을 위한 변수
        NoticeSummaryDto previousNotice = null;

        // 다음 공지사항을 위한 변수
        NoticeSummaryDto nextNotice = null;

        // 현재 공지사항이 첫 번째가 아닌 경우에만 이전 공지사항을 설정
        if (currentIndex > 0) {
            Notice previousNoticeEntity = notices.get(currentIndex - 1); // 이전 공지사항 엔티티를 가져옴
            previousNotice = new NoticeSummaryDto(previousNoticeEntity.getId(), previousNoticeEntity.getTitle());
        }


        // 현재 공지사항이 마지막이 아닌 경우에만 다음 공지사항을 설정
        if (currentIndex < notices.size() - 1) {
            Notice nextNoticeEntity = notices.get(currentIndex + 1); // 다음 공지사항 엔티티를 가져옴
            nextNotice = new NoticeSummaryDto(nextNoticeEntity.getId(), nextNoticeEntity.getTitle());
        }

        // 현재 공지사항 DTO 생성
        NoticeDetailDto currentNoticeDto = NoticeDetailDto.fromEntity(notice);

        // 이전 공지사항, 현재 공지사항, 다음 공지사항을 포함한 DTO를 반환
        return new NoticeDetailWithNavigationDto(currentNoticeDto, previousNotice, nextNotice);
    }
}
