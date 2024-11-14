package com.example.humorie.consultant.review.mapper;

import com.example.humorie.consultant.review.dto.ReviewReq;
import com.example.humorie.consultant.review.dto.ReviewRes;
import com.example.humorie.consultant.review.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface ReviewMapper {

    ReviewMapper INSTANCE = Mappers.getMapper(ReviewMapper.class);

    @Mapping(source = "id", target = "reviewId")
    ReviewRes toReviewRes(Review review);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "account", ignore = true)
    @Mapping(target = "counselor", ignore = true)
    Review toReview(ReviewReq reviewReq);

    List<ReviewRes> toReviewResList(List<Review> reviews);

}
