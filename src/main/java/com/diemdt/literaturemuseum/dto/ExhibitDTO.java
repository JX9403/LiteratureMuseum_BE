package com.diemdt.literaturemuseum.dto;

import com.diemdt.literaturemuseum.entity.Exhibit;
import com.diemdt.literaturemuseum.entity.File;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExhibitDTO {
    private Long id;
    @NotBlank(message = "Exhibit name is required")
    private String name ;
    @NotBlank(message = "Exhibit content is required")
    private String content;

    private Exhibit.Type type;

    private Integer view;

    private List<File> files = new ArrayList<>();
}
