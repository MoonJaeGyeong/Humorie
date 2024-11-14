package com.example.humorie.consultant.review.mapper;

import com.example.humorie.consultant.review.dto.TagReq;
import com.example.humorie.consultant.review.dto.TagRes;
import com.example.humorie.consultant.review.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TagMapper {

    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    @Mapping(source = "id", target = "tagId")
    TagRes toTagRes(Tag tag);

    @Mapping(target = "id", ignore = true)
    Tag toReviewTag(TagReq tagReq);

    List<TagRes> toTagResList(List<Tag> tags);

}
