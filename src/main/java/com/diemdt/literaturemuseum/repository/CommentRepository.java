package com.diemdt.literaturemuseum.repository;

import com.diemdt.literaturemuseum.entity.Comment;
import com.diemdt.literaturemuseum.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // Tìm tất cả bình luận theo targetType và targetId (Hỗ trợ phân trang)
    Page<Comment> findByTargetTypeAndTargetId(Pageable pageable, Comment.Type targetType, Long targetId);

    // Tìm tất cả bình luận của một người dùng (Hỗ trợ phân trang)
    Page<Comment> findByUser(Pageable pageable, User user);
    @Query("SELECT c.user.id FROM Comment c GROUP BY c.user.id ORDER BY COUNT(c.id) DESC LIMIT 1")
    Long findTopUserByCommentCount();

}
