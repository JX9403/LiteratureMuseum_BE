package com.diemdt.literaturemuseum.mapper;

import com.diemdt.literaturemuseum.dto.CommentDTO;
import com.diemdt.literaturemuseum.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(target = "userId",source = "user.id")
    CommentDTO toDTO(Comment comment);
    @Mapping(target = "user.id", source = "userId")
    Comment toEntity(CommentDTO commentDTO);
}
