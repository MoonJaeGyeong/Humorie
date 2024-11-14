package com.example.humorie.consultant.counselor.service;

import com.example.humorie.account.entity.AccountDetail;
import com.example.humorie.account.jwt.PrincipalDetails;
import com.example.humorie.consultant.counselor.dto.BookmarkDto;
import com.example.humorie.consultant.counselor.dto.CounselorDto;
import com.example.humorie.consultant.counselor.entity.Bookmark;
import com.example.humorie.consultant.counselor.entity.Counselor;
import com.example.humorie.consultant.counselor.entity.Symptom;
import com.example.humorie.consultant.counselor.repository.BookmarkRepository;
import com.example.humorie.consultant.counselor.repository.SymptomRepository;
import com.example.humorie.global.exception.ErrorCode;
import com.example.humorie.global.exception.ErrorException;
import com.example.humorie.global.service.CommonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final SymptomRepository symptomRepository;
    private final CommonService commonService;

    @Transactional
    public String addBookmark(PrincipalDetails principal, long counselorId) {
        if(principal == null){
            throw new ErrorException(ErrorCode.NONE_EXIST_USER);
        }

        AccountDetail account = principal.getAccountDetail();
        Counselor counselor = commonService.getCounselorById(counselorId);

        if (bookmarkRepository.existsByAccountAndCounselor(account, counselor)) {
            throw new ErrorException(ErrorCode.BOOKMARK_EXISTS);
        }

        Bookmark bookmark = Bookmark.builder()
                .account(account)
                .counselor(counselor)
                .build();

        bookmarkRepository.save(bookmark);

        return "Bookmark added successfully";
    }

    @Transactional
    public String removeBookmark(PrincipalDetails principal, long counselorId) {
        if(principal == null){
            throw new ErrorException(ErrorCode.NONE_EXIST_USER);
        }

        AccountDetail account = principal.getAccountDetail();
        Counselor counselor = commonService.getCounselorById(counselorId);

        Bookmark bookmark = bookmarkRepository.findByAccountAndCounselor(account, counselor)
                .orElseThrow(() -> new ErrorException(ErrorCode.NONE_EXIST_BOOKMARK));


        bookmarkRepository.deleteById(bookmark.getId());

        return "Bookmark removed successfully";
    }

    @Transactional(readOnly = true)
    public List<BookmarkDto> getAllBookmarks(PrincipalDetails principal) {
        if(principal == null){
            throw new ErrorException(ErrorCode.NONE_EXIST_USER);
        }

        AccountDetail account = principal.getAccountDetail();
        List<Bookmark> bookmarks = bookmarkRepository.findAllByAccount(account);

        return bookmarks.stream()
                .map(this::convertToBookmarkDto)
                .collect(Collectors.toList());
    }

    private BookmarkDto convertToBookmarkDto(Bookmark bookmark) {
        BookmarkDto bookmarkDto = new BookmarkDto();
        bookmarkDto.setBookmarkId(bookmark.getId());
        bookmarkDto.setCreatedAt(bookmark.getCreatedAt());

        CounselorDto counselorDto = convertToCounselorDto(bookmark.getCounselor());
        bookmarkDto.setCounselor(counselorDto);

        return bookmarkDto;
    }

    private CounselorDto convertToCounselorDto(Counselor counselor) {
        CounselorDto counselorDto = new CounselorDto();

        counselorDto.setCounselorId(counselor.getId());
        counselorDto.setName(counselor.getName());

        Set<String> symptoms = symptomRepository.findByCounselorId(counselor.getId()).stream()
                .map(Symptom::getSymptom)
                .collect(Collectors.toSet());
        counselorDto.setSymptoms(symptoms);

        return counselorDto;
    }

}
