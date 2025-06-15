package com.diemdt.literaturemuseum.controller;

import com.diemdt.literaturemuseum.dto.WorkDTO;
import com.diemdt.literaturemuseum.service.WorkService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/works")
@RequiredArgsConstructor
public class WorkController {
    private final WorkService workService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<WorkDTO> createWork (@RequestBody @Valid WorkDTO workDTO){
        return ResponseEntity.ok(workService.createWork(workDTO));
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<WorkDTO> updateWork(@PathVariable Long id,
                                                      @RequestBody @Valid WorkDTO workDTO){
        return ResponseEntity.ok(workService.updateWork(id, workDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteWork (@PathVariable Long id ){
        workService.deleteWork(id);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkDTO> getWork(@PathVariable Long id){
        return ResponseEntity.ok(workService.getWork(id));
    }

    @GetMapping
    public ResponseEntity<Page<WorkDTO>> getAllWorks(@PageableDefault(size = 10) Pageable pageable, @RequestParam(required = false) String search, @RequestParam(required = false) Long authorId){
        return ResponseEntity.ok(workService.getAllWorks(  pageable, search, authorId));
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<Page<WorkDTO>> getAllWorksByAuthor(@PageableDefault(size = 10) Pageable pageable, @RequestParam(required = false) String authorId){
        return ResponseEntity.ok(workService.getAllWorksByAuthor(pageable, authorId));
    }
}
