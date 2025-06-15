package com.diemdt.literaturemuseum.repository;

import com.diemdt.literaturemuseum.entity.Artifact;
import com.diemdt.literaturemuseum.entity.Exhibit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitRepository extends JpaRepository<Exhibit, Long> {
    Page<Exhibit> findByNameContainingIgnoreCase(Pageable pageable, String search);
    Page<Exhibit> findByNameContainingIgnoreCaseAndType(Pageable pageable, String name, Exhibit.Type type);

    Page<Exhibit> findByType(Pageable pageable, Exhibit.Type type);

}
