package com.diemdt.literaturemuseum.repository;

import com.diemdt.literaturemuseum.entity.Artifact;
import com.diemdt.literaturemuseum.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

        Page<News> findByNameContainingIgnoreCase( Pageable pageable,String search);


}
