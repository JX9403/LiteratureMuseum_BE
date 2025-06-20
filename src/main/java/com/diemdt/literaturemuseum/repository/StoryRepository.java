package com.diemdt.literaturemuseum.repository;

import com.diemdt.literaturemuseum.entity.Artifact;
import com.diemdt.literaturemuseum.entity.Story;
import com.diemdt.literaturemuseum.entity.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {
    Page<Story> findByNameContainingIgnoreCase(String search, Pageable pageable);
    Page<Story> findByAuthorId(Long id, Pageable pageable);
    Page<Story> findByNameContainingIgnoreCaseAndAuthorId(String search, Long id, Pageable pageable);
    Optional<Story> findTopByOrderByViewDesc();

}

