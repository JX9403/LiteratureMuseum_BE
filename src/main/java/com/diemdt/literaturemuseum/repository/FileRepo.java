package com.diemdt.literaturemuseum.repository;

import com.diemdt.literaturemuseum.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepo extends JpaRepository<File, Long> {
    List<File> findByTargetTypeAndTargetId(File.Type targetType, Long targetId);

    List<File> findByTargetTypeAndTargetIdIn(File.Type targetType, List<Long> targetIds);


}