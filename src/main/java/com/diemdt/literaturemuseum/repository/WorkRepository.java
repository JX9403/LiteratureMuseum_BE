package com.diemdt.literaturemuseum.repository;

import com.diemdt.literaturemuseum.entity.Artifact;
import com.diemdt.literaturemuseum.entity.Author;
import com.diemdt.literaturemuseum.entity.Story;
import com.diemdt.literaturemuseum.entity.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {
    Page<Work> findByNameContainingIgnoreCase(String search, Pageable pageable);
    Page<Work> findByAuthorId(Long id, Pageable pageable);
    Page<Work> findByNameContainingIgnoreCaseAndAuthorId(String search, Long id, Pageable pageable);

}
