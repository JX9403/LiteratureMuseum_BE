package com.diemdt.literaturemuseum.dto;

import com.diemdt.literaturemuseum.entity.Blog;
import com.diemdt.literaturemuseum.entity.File;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
public class BlogDTO {
    private Long id;
    @NotBlank(message = "Blog name is required")
    private String name;
    @NotBlank(message = "Blog content is required")
    private String content;

    private Long userId;
    private LocalDateTime createdAt ;
    private Blog.Status status;
    private Integer view;

    private List<File> files = new ArrayList<>();
}
