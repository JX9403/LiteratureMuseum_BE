package com.diemdt.literaturemuseum.dto;

import com.diemdt.literaturemuseum.entity.File;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
public class NewsDTO {
    private Long id;
    @NotBlank(message = "News name is required")
    private String name;
    @NotBlank(message = "News content is required")
    private String content;
    private Integer view;
    private LocalDateTime createdAt;


    private List<File> files = new ArrayList<>();
}
