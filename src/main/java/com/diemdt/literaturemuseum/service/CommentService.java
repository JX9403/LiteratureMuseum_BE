package com.diemdt.literaturemuseum.service;

import com.diemdt.literaturemuseum.dto.CommentDTO;
import com.diemdt.literaturemuseum.entity.Comment;
import com.diemdt.literaturemuseum.exception.ResourceNotFoundException;
import com.diemdt.literaturemuseum.mapper.CommentMapper;
import com.diemdt.literaturemuseum.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final WorkRepository workRepository;
    private final ArtifactRepository artifactRepository;
    private final BlogRepository blogRepository;
    private final ExhibitRepository exhibitRepository;
    private final NewsRepository newsRepository;
    private final StoryRepository storyRepository;
    private final AuthorRepository authorRepository;
    private final CommentMapper commentMapper;

    // Tạo bình luận
    public CommentDTO createComment(Long userId , CommentDTO commentDTO) {
        validateTarget(commentDTO.getTargetId(), commentDTO.getTargetType());

        Comment comment = new Comment();
        comment.setTargetType(commentDTO.getTargetType());
        comment.setTargetId(commentDTO.getTargetId());
        comment.setUser(userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found")));
        comment.setContent(commentDTO.getContent());

        Comment savedComment = commentRepository.save(comment);
        return commentMapper.toDTO(savedComment);
    }

    // Lấy danh sách bình luận theo targetId và targetType
    public Page<CommentDTO> getCommentsByTarget(Pageable pageable, Comment.Type targetType, Long targetId) {
        validateTarget(targetId, targetType);

        Page<Comment> comments = commentRepository.findByTargetTypeAndTargetId(pageable, targetType, targetId);
        return comments.map(commentMapper::toDTO);
    }

    // Cập nhật bình luận
    public CommentDTO updateComment(Long id, CommentDTO commentDTO) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        comment.setContent(commentDTO.getContent());

        Comment updatedComment = commentRepository.save(comment);
        return  commentMapper.toDTO(updatedComment);
    }

    // Xóa bình luận
    public void deleteComment(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment not found"));
        commentRepository.delete(comment);
    }

    // Kiểm tra targetId có hợp lệ không
    private void validateTarget(Long targetId, Comment.Type targetType) {
        if (targetId == null || targetId <= 0) {
            throw new IllegalStateException( "Target ID must be a valid positive number");
        }

        boolean exists;
        switch (targetType) {
            case WORK:
                exists = workRepository.existsById(targetId);
                break;
            case ARTIFACT:
                exists = artifactRepository.existsById(targetId);
                break;
            case BLOG:
                exists = blogRepository.existsById(targetId);
                break;
            case EXHIBIT:
                exists = exhibitRepository.existsById(targetId);
                break;
            case NEWS:
                exists = newsRepository.existsById(targetId);
                break;
            case STORY:
                exists = storyRepository.existsById(targetId);
                break;
            case AUTHOR:
                exists = authorRepository.existsById(targetId);
                break;
            default:
                throw new IllegalStateException( "Invalid target type");
        }

        if (!exists) {
            throw new ResourceNotFoundException( "Target not found for given ID and Type");
        }
    }
}
