package com.diemdt.literaturemuseum.mapper;

import com.diemdt.literaturemuseum.dto.AwardDTO;
import com.diemdt.literaturemuseum.entity.Award;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AwardMapper {
    AwardDTO toDTO(Award award);
    Award toEntity(AwardDTO awardDTO);
}
