package com.diemdt.literaturemuseum.repository;

import com.diemdt.literaturemuseum.entity.Artifact;
import com.diemdt.literaturemuseum.entity.Blog;
import com.diemdt.literaturemuseum.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Long> {
    Page<Blog> findByNameContainingIgnoreCase(Pageable pageable, String search);

    Page<Blog> findByUserId(Pageable pageable, Long userId);

    Page<Blog> findByUserIdAndNameContainingIgnoreCase(Pageable pageable, Long userId, String search);
    Optional<Blog> findTopByOrderByViewDesc();

    @Query("SELECT b.user.id FROM Blog b GROUP BY b.user.id ORDER BY COUNT(b.id) DESC LIMIT 1")
    Long findTopUserByBlogCount();

}
