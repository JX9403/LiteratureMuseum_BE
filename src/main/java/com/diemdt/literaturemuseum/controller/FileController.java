package com.diemdt.literaturemuseum.controller;

import com.diemdt.literaturemuseum.dto.AssignFileRequest;
import com.diemdt.literaturemuseum.entity.File;
import com.diemdt.literaturemuseum.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<Object> saveFile(@RequestParam(required = false) MultipartFile file,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) File.Type type,
                                           @RequestParam(required = false) Long targetId,
                                           @RequestParam(required = false) String description) {
        if (file == null || file.isEmpty() || name == null || name.isEmpty() || type == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File, Name, Type and TargetId are required");
        }
        return ResponseEntity.ok(fileService.saveFile(file, name, type, targetId, description));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<File>> getAllFiles() {
        return ResponseEntity.ok(fileService.getAllFiles());
    }

    // Optional: API lấy file theo targetType và targetId
    @GetMapping("/by-target")
    public ResponseEntity<List<File>> getFilesByTarget(@RequestParam File.Type type,
                                                       @RequestParam Long targetId) {
        return ResponseEntity.ok(fileService.getFilesByTarget(type, targetId));
    }
    @PutMapping("/{id}/assign")
    public ResponseEntity<File> assignFileToTarget(@PathVariable Long id,
                                                   @RequestBody AssignFileRequest request) {
        File file = fileService.assignToTarget(id, request.getType(), request.getTargetId());
        return ResponseEntity.ok(file);
    }

    @GetMapping("/by-targets")
    public ResponseEntity<List<File>> getByTargets(
            @RequestParam File.Type type,
            @RequestParam List<Long> ids) {
        return ResponseEntity.ok(fileService.getFilesByTargetBatch(type, ids));
    }


}
