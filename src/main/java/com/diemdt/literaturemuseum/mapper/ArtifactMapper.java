package com.diemdt.literaturemuseum.mapper;

import com.diemdt.literaturemuseum.dto.ArtifactDTO;
import com.diemdt.literaturemuseum.entity.Artifact;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ArtifactMapper {

    ArtifactDTO toDTO(Artifact artifact);

    Artifact toEntity(ArtifactDTO artifactDTO);


}
