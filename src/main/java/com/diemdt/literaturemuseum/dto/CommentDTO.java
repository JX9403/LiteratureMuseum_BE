package com.diemdt.literaturemuseum.dto;

import com.diemdt.literaturemuseum.entity.Comment;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class CommentDTO {
    private Long id;
    @NotBlank(message = "Comment content is required")
    private String content;

    private Long userId;

    private Comment.Type targetType;

    private Long targetId;
    private LocalDateTime createdAt;

}
