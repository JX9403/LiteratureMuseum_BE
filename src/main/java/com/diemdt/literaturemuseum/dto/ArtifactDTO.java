package com.diemdt.literaturemuseum.dto;

import com.diemdt.literaturemuseum.entity.File;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ArtifactDTO {
    private Long id;
    @NotBlank(message = "Artifact name is required")
    private String name;

    @NotBlank(message = "Artifact content is required")
    private String content ;

    private String model ;

    private String usdz;

    private Integer view;

    private List<File> files = new ArrayList<>();

}
