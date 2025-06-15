package com.diemdt.literaturemuseum.mapper;

import com.diemdt.literaturemuseum.dto.BlogDTO;
import com.diemdt.literaturemuseum.entity.Blog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BlogMapper {
    @Mapping(target = "userId", source = "user.id")
    BlogDTO toDTO(Blog blog);
    @Mapping(target = "user.id", source = "userId")
    Blog toEntity (BlogDTO blogDTO);

}
