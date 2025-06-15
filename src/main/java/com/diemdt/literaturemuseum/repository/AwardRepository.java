package com.diemdt.literaturemuseum.repository;

import com.diemdt.literaturemuseum.entity.Artifact;
import com.diemdt.literaturemuseum.entity.Award;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AwardRepository extends JpaRepository<Award, Long> {
    Page<Award> findByNameContainingIgnoreCase(Pageable pageable, String search);
}
