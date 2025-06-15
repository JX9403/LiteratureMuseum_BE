package com.diemdt.literaturemuseum.service;

import com.diemdt.literaturemuseum.entity.File;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {
    File saveFile(MultipartFile file, String name, File.Type fileType , Long targetId , String description);
    List<File> getAllFiles();
    List<File> getFilesByTarget(File.Type fileType, Long targetId);
    File assignToTarget(Long fileId, File.Type fileType, Long targetId);
    public List<File> getFilesByTargetBatch(File.Type fileType, List<Long> targetIds);
}
