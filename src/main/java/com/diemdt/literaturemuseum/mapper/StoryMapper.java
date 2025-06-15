package com.diemdt.literaturemuseum.mapper;

import com.diemdt.literaturemuseum.dto.StoryDTO;
import com.diemdt.literaturemuseum.entity.Story;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StoryMapper {
    @Mapping(target = "authorId", source = "author.id")
    StoryDTO toDTO(Story story);
    @Mapping(target = "author.id", source = "authorId")
    Story toEntity (StoryDTO storyDTO);

}
