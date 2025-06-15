package com.diemdt.literaturemuseum.dto;

import com.diemdt.literaturemuseum.entity.File;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class WorkDTO {
    private Long id;
    @NotBlank(message = "Work name is required")
    private String name;
    @NotBlank(message = "Work content is required")
    private String content;

    private Long authorId;
    private Integer view;

    private List<File> files = new ArrayList<>();
}
