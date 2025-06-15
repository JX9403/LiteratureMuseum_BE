package com.diemdt.literaturemuseum.repository;

import com.diemdt.literaturemuseum.dto.ArtifactDTO;
import com.diemdt.literaturemuseum.entity.Artifact;
import com.diemdt.literaturemuseum.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.zip.Adler32;

@Repository
public interface ArtifactRepository extends JpaRepository<Artifact, Long> {
    Page<Artifact> findByNameContainingIgnoreCase(Pageable pageable, String search);
}
