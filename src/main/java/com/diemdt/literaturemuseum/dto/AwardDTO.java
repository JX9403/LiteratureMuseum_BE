package com.diemdt.literaturemuseum.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AwardDTO {
    private Long id;
    @NotBlank(message = "Award name is required")
    private String name ;
    @NotBlank(message = "Award description is required")
    private String description;
}
