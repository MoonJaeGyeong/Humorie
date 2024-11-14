package com.example.humorie.consultant.review.controller;

import com.example.humorie.account.jwt.PrincipalDetails;
import com.example.humorie.consultant.review.dto.TagReq;
import com.example.humorie.consultant.review.dto.TagRes;
import com.example.humorie.consultant.review.service.TagService;
import com.example.humorie.global.exception.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/review")
public class TagController {

    private final TagService tagService;

    @PostMapping("/tag")
    @Operation(summary = "태그 등록")
    public ErrorResponse<String> createTag(@RequestBody TagReq tagReq,
                                           @AuthenticationPrincipal PrincipalDetails principal) {
        return new ErrorResponse<>(tagService.createTag(principal, tagReq));
    }

    @GetMapping("/tag/{tagName}")
    @Operation(summary = "태그 단건 조회")
    public ErrorResponse<TagRes> getTagByName(@PathVariable String tagName,
                                              @AuthenticationPrincipal PrincipalDetails principal) {
        return new ErrorResponse<>(tagService.getTagByName(principal, tagName));
    }

    @GetMapping("/tags")
    @Operation(summary = "태그 목록 조회")
    public ErrorResponse<List<TagRes>> getAllTags(@AuthenticationPrincipal PrincipalDetails principal) {
        return new ErrorResponse<>(tagService.getAllTags(principal));
    }

    @DeleteMapping("/tag/{tagId}")
    @Operation(summary = "태그 삭제")
    public ErrorResponse<String> deleteTag(@PathVariable long tagId,
                                           @AuthenticationPrincipal PrincipalDetails principal) {
        return new ErrorResponse<>(tagService.deleteTag(principal, tagId));
    }

}
