package com.diemdt.literaturemuseum.mapper;

import com.diemdt.literaturemuseum.dto.NewsDTO;
import com.diemdt.literaturemuseum.entity.News;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewsMapper {

    NewsDTO toDTO(News news);

    News toEntity(NewsDTO newsDTO);


}
