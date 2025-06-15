package com.diemdt.literaturemuseum.controller;

import com.diemdt.literaturemuseum.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.diemdt.literaturemuseum.dto.CommentDTO;
import com.diemdt.literaturemuseum.entity.Comment.Type;
import com.diemdt.literaturemuseum.service.CommentService;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // Thêm bình luận vào một target cụ thể
    @PostMapping("/{targetType}/{targetId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentDTO> addComment(
                                                 @AuthenticationPrincipal UserDetails userDetails,
                                                 @Valid @RequestBody CommentDTO commentDTO) {
        Long userId = ((User) userDetails).getId();
        return ResponseEntity.ok(commentService.createComment(userId, commentDTO));
    }

    // Lấy bình luận theo targetId và targetType, hỗ trợ phân trang
    @GetMapping("/{targetType}/{targetId}")
    public ResponseEntity<Page<CommentDTO>> getCommentsByTarget(@PathVariable Type targetType,
                                                                @PathVariable Long targetId,
                                                                Pageable pageable) {
        return ResponseEntity.ok(commentService.getCommentsByTarget(pageable, targetType, targetId));
    }

    // Cập nhật bình luận
    @PutMapping("/{commentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommentDTO> updateComment(@PathVariable Long commentId,

                                                    @Valid @RequestBody CommentDTO commentDTO) {

        return ResponseEntity.ok(commentService.updateComment(commentId,  commentDTO));
    }

    // Xóa bình luận
    @DeleteMapping("/{commentId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
