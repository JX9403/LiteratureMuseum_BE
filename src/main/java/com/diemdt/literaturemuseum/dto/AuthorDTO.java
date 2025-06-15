package com.diemdt.literaturemuseum.dto;

import com.diemdt.literaturemuseum.entity.Author;
import com.diemdt.literaturemuseum.entity.File;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AuthorDTO {
    private Long id;
    @NotBlank(message = "Author name is required")
    private String name;
    @Positive(message = "Cannot be negative")
    private Integer birthYear ;
    @Positive(message = "Cannot be negative")
    private Integer deathYear;
    @NotBlank(message = "Author content is required")
    private String content ;

    private Author.Type type;
    private Long awardId;

    private List<File> files = new ArrayList<>();
}
