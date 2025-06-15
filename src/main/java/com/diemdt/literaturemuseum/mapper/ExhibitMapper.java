package com.diemdt.literaturemuseum.mapper;

import com.diemdt.literaturemuseum.dto.ExhibitDTO;
import com.diemdt.literaturemuseum.entity.Exhibit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ExhibitMapper {

    ExhibitDTO toDTO(Exhibit exhibit);

    Exhibit toEntity(ExhibitDTO exhibitDTO);


}
