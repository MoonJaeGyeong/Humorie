package com.example.humorie.consultant.search.controller;

import com.example.humorie.consultant.search.dto.CounselorDto;
import com.example.humorie.consultant.search.dto.SearchReq;
import com.example.humorie.consultant.search.service.SearchService;
import com.example.humorie.global.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    @Operation(summary = "전체 상담사 리스트 조회")
    public ErrorResponse<List<CounselorDto>> getAllCounselors() {
        List<CounselorDto> counselors = searchService.getAllCounselors();
        return new ErrorResponse<>(counselors);
    }

    @PostMapping("/keywords")
    @Operation(summary = "키워드 선택으로 상담사 검색")
    public ErrorResponse<List<CounselorDto>> searchByKeywords(@RequestBody SearchReq searchReq,
                                                               @RequestParam(required = false) String counselingMethod,
                                                               @RequestParam(required = false) String gender,
                                                               @RequestParam(required = false) String region,
                                                               @RequestParam(required = false) String order) {
        List<CounselorDto> counselors = searchService.searchByKeywords(searchReq, counselingMethod, gender, region, order);

        return new ErrorResponse<>(counselors);
    }

    @GetMapping("/single-keyword")
    @Operation(summary = "검색어로 상담사 검색")
    public ErrorResponse<List<CounselorDto>> searchBySingleKeyword(@RequestParam String keyword,
                                                                    @RequestParam(required = false) String counselingMethod,
                                                                    @RequestParam(required = false) String gender,
                                                                    @RequestParam(required = false) String region,
                                                                    @RequestParam(required = false) String order) {
        List<CounselorDto> counselors = searchService.searchBySingleKeyword(keyword, counselingMethod, gender, region, order);
        return new ErrorResponse<>(counselors);
    }

    @GetMapping("/conditions")
    @Operation(summary = "조건으로 상담사 검색")
    public ErrorResponse<List<CounselorDto>> searchByConditions(
            @RequestParam(required = false) String counselingMethod,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String region,
            @RequestParam(required = false) String order) {
        List<CounselorDto> counselors = searchService.searchByConditions(counselingMethod, gender, region, order);

        return new ErrorResponse<>(counselors);
    }

}
