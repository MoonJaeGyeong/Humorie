package com.example.humorie.admin.mapper;

import com.example.humorie.admin.dto.ConsultDetailDto;
import com.example.humorie.consultant.consult_detail.entity.ConsultDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    AdminMapper INSTANCE = Mappers.getMapper(AdminMapper.class);

    @Mapping(target = "account", ignore = true)
    @Mapping(target = "counselor", ignore = true)
    @Mapping(target = "counselDate", ignore = true)
    ConsultDetail dtoToConsultDetail(ConsultDetailDto consultDetailDto);

}