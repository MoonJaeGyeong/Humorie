package com.example.humorie.global.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    /**
     * 1000 : 요청 성공
     */

    SUCCESS(true, 1000, "요청에 성공했습니다."),


    /**
     * 2000 : Request 오류
     */

    // common
    REQUEST_ERROR(false, 2000, "잘못된 입력입니다."),

    // user
    EMPTY_JWT(false, 2001, "토큰을 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 토큰입니다."),
    NONE_EXIST_USER(false, 2003, "존재하지 않는 사용자입니다."),
    ID_EXISTS(false, 2004, "중복된 아이디입니다."),
    PASSWORD_MISMATCH(false, 2005, "비밀번호가 일치하지 않습니다."),
    INVALID_EMAIL(false, 2006, "잘못된 이메일 형식입니다."),
    INVALID_PASSWORD(false, 2007, "잘못된 비밀번호 형식입니다. 비밀번호는 최소 8자 이상 16자 이하이며, 적어도 하나의 숫자와 알파벳, 특수문자가 포함되어야 합니다."),
    INVALID_ID(false, 2008, "잘못된 아이디 형식입니다. 아이디는 최소 6자 이상이어야 하며, 소문자 알파벳과 숫자로 구성되어야 합니다."),
    INVALID_NAME(false, 2009, "잘못된 이름 형식입니다."),
    EMPTY_NAME(false, 2010, "이름을 입력해주세요"),
    EMPTY_EMAIL(false, 2011, "이메일을 입력해주세요"),

    // reservation
    NONE_EXIST_RESERVATION(false, 2012, "존재하지 않는 예약입니다."),
    INCOMPLETE_PAYMENT(false, 2013, "완료되지 않은 결제입니다."),
    SUSPECTED_PAYMENT_FORGERY(false, 2014, "위변조 의심 결제입니다."),
    EXCEED_POINT(false, 2015, "포인트가 초과되었습니다."),

    // review
    NONE_EXIST_REVIEW(false, 2016, "존재하지 않는 리뷰입니다."),
    REVIEW_PERMISSION_DENIED(false, 2017, "본인이 작성한 리뷰만 수정, 삭제할 수 있습니다."),
    NONE_EXIST_TAG(false, 2018, "존재하지 않는 태그입니다."),
    DUPLICATE_TAG_NAME(false, 2019, "중복된 태그 이름입니다."),
    MAX_TAG_LIMIT_EXCEEDED(false, 2020, "등록할 수 있는 태그의 수는 최대 5개 입니다."),

    // mypage
    PASSWORD_CONFIRMATION_EMPTY(false,2021,"비밀번호 확인을 입력해주세요."),
    EMPTY_PASSWORD(false,2022,"비밀번호를 입력해주세요."),

    // admin
    INVALID_DATE_TIME_FORMAT(false, 2023, "날짜 입력 형식이 잘못되었습니다. 날짜는 yyyy-MM-dd HH:mm 형식으로 입력되어야 합니다."),
    ACCESS_DENIED(false, 2024, "허용되지 않은 권한입니다."),
    EMPTY_TITLE(false, 2025, "제목을 입력해주세요."),
    EMPTY_CONTENT(false, 2026, "내용을 입력해주세요."),
    TITLE_TOO_LONG(false, 2027, "제목은 최대 50자까지 입력 가능합니다."),
    CONTENT_TOO_LONG(false,2028,"내용은 최대 1000자까지 입력 가능합니다."),

    /**
     * 3000 : Response 오류
     */

    // user
    SEND_EMAIL_FAILED(false, 3001, "이메일 전송에 실패했습니다."),
    VERIFICATION_FAILED(false, 3002, "이메일 인증에 실패했습니다."),

    // counselor
    NON_EXIST_COUNSELOR(false, 3003, "존재하지 않는 상담사입니다."),
    BOOKMARK_EXISTS(false, 3004, "해당 상담사에 대한 북마크가 이미 존재합니다."),
    NONE_EXIST_BOOKMARK(false, 3005, "존재하지 않는 북마크입니다."),
    SEARCH_FAILED(false, 3006, "상담사에 대한 검색을 실패했습니다."),
    FAILED_PAYMENT(false, 3007, "결제 실패"),

    // consult_detail
    NONE_EXIST_CONSULT_DETAIL(false, 3008, "존재하지 않는 상담 내역입니다."),
    CONSULT_DETAIL_NOT_COMPLETED(false, 3009, "상담 내용을 작성하고 있는 중이에요"),
    NO_RECENT_CONSULT_DETAIL(false, 3010, "최근 상담 내역이 없습니다."),

    // notice
    NONE_EXIST_NOTICE(false, 3011,"존재하지 않는 공지사항입니다."),
    NO_CONTENT(false, 3012,"표시할 콘텐츠가 없습니다."),
    INVALID_PAGE_NUMBER(false, 3013,"페이지 번호가 전체 페이지 수를 초과했습니다."),
    NEGATIVE_PAGE_NUMBER(false, 3014,"페이지 번호는 0 이상이어야 합니다."),
    INVALID_PAGE_SIZE(false, 3015,"페이지 크기가 최대 허용 값을 초과했습니다."),
    NEGATIVE_PAGE_SIZE(false, 3016,"페이지 크기는 0보다 커야 합니다."),
    NO_SEARCH_RESULTS(false, 3017,"검색 결과가 없습니다.");


    private final boolean isSuccess;

    private final int code;

    private final String message;

}
