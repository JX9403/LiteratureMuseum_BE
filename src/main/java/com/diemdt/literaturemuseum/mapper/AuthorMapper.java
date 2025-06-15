package com.diemdt.literaturemuseum.mapper;

import com.diemdt.literaturemuseum.dto.AuthorDTO;
import com.diemdt.literaturemuseum.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    @Mapping(target = "awardId", source = "award.id")
    AuthorDTO toDTO(Author author);
    @Mapping(target = "award.id", source = "awardId")
    Author toEntity (AuthorDTO authorDTO);

}
