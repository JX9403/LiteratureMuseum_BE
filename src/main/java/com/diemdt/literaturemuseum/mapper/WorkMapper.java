package com.diemdt.literaturemuseum.mapper;

import com.diemdt.literaturemuseum.dto.WorkDTO;
import com.diemdt.literaturemuseum.entity.Work;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkMapper {
    @Mapping(target = "authorId", source = "author.id")
    WorkDTO toDTO(Work work);
    @Mapping(target = "author.id", source = "authorId")
    Work toEntity (WorkDTO workDTO);

}
