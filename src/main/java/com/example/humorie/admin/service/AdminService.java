package com.example.humorie.admin.service;

import com.example.humorie.account.entity.AccountDetail;
import com.example.humorie.account.entity.AccountRole;
import com.example.humorie.account.jwt.PrincipalDetails;
import com.example.humorie.account.service.EmailService;
import com.example.humorie.admin.dto.ConsultDetailDto;
import com.example.humorie.admin.dto.NoticeDto;
import com.example.humorie.admin.mapper.AdminMapper;
import com.example.humorie.consultant.consult_detail.entity.ConsultDetail;
import com.example.humorie.consultant.consult_detail.repository.ConsultDetailRepository;
import com.example.humorie.consultant.counselor.entity.Counselor;
import com.example.humorie.consultant.counselor.repository.CounselorRepository;
import com.example.humorie.global.exception.ErrorCode;
import com.example.humorie.global.exception.ErrorException;
import com.example.humorie.notice.entity.Notice;
import com.example.humorie.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final CounselorRepository counselorRepository;
    private final ConsultDetailRepository consultDetailRepository;
    private final NoticeRepository noticeRepository;
    private final AdminMapper adminMapper;
    private final EmailService emailService;

    @Transactional
    public String saveConsultDetail(ConsultDetailDto consultDetailDto, Long counselorId, PrincipalDetails principal) {
        AccountDetail account = principal.getAccountDetail();

        Counselor counselor = counselorRepository.findById(counselorId)
                .orElseThrow(() -> new ErrorException(ErrorCode.NON_EXIST_COUNSELOR));

        LocalDateTime counselDate = parseCounselDateTime(consultDetailDto.getCounselDate());

        ConsultDetail consultDetail = adminMapper.dtoToConsultDetail(consultDetailDto);
            consultDetail.setAccount(account);
            consultDetail.setCounselor(counselor);
            consultDetail.setCounselDate(counselDate.toLocalDate());
            consultDetail.setCounselTime(counselDate.toLocalTime());
            consultDetail.setStatus(true);
            counselor.setCounselingCount(counselor.getCounselingCount() + 1);

        consultDetailRepository.save(consultDetail);
        counselorRepository.save(counselor);

        try {
            emailService.sendConsultationCompletionEmail(account.getEmail(), consultDetailDto.getCounselDate(), counselor.getName());
        } catch (Exception e) {
            throw new ErrorException(ErrorCode.SEND_EMAIL_FAILED);
        }

        return "Consultation details saved successfully";
    }

    private LocalDateTime parseCounselDateTime(String dateTimeString) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            return LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            throw new ErrorException(ErrorCode.INVALID_DATE_TIME_FORMAT);
        }
    }

    @Transactional
    public String saveNotice(NoticeDto noticeDto, PrincipalDetails principal){
        AccountDetail account = principal.getAccountDetail();

        // 제목과 내용 유효성 검사
        validateNotice(noticeDto);

        // Notice 엔티티 생성
        Notice notice = noticeDto.toEntity(account.getName()); // DTO를 엔티티로 변환

        // 공지사항 저장
        noticeRepository.save(notice);

        return "Notice Saved Successfully";
    }

    // 유효성 검사 메서드
    private void validateNotice(NoticeDto noticeDto) {
        if (noticeDto.getTitle() == null || noticeDto.getTitle().isEmpty() || noticeDto.getTitle().trim().isEmpty()) {
            throw new ErrorException(ErrorCode.EMPTY_TITLE); // 제목 비어 있음
        }
        if (noticeDto.getTitle().length() > 50) {
            throw new ErrorException(ErrorCode.TITLE_TOO_LONG); // 제목 길이 초과
        }
        if (noticeDto.getContent() == null || noticeDto.getContent().isEmpty() || noticeDto.getContent().trim().isEmpty()) {
            throw new ErrorException(ErrorCode.EMPTY_CONTENT); // 내용 비어 있음
        }
        if (noticeDto.getContent().length() > 1000) {
            throw new ErrorException(ErrorCode.CONTENT_TOO_LONG); // 제목 길이 초과
        }
    }
}
