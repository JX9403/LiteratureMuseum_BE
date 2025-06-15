package com.diemdt.literaturemuseum.controller;

import com.diemdt.literaturemuseum.dto.ExhibitDTO;
import com.diemdt.literaturemuseum.service.ExhibitService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exhibits")
@RequiredArgsConstructor
public class ExhibitController {
    private final ExhibitService exhibitService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExhibitDTO> createExhibit (@RequestBody @Valid ExhibitDTO exhibitDTO){
        return ResponseEntity.ok(exhibitService.createExhibit(exhibitDTO));
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExhibitDTO> updateExhibit(@PathVariable Long id,
                                                      @RequestBody @Valid ExhibitDTO exhibitDTO){
        return ResponseEntity.ok(exhibitService.updateExhibit(id, exhibitDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteExhibit (@PathVariable Long id ){
        exhibitService.deleteExhibit(id);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExhibitDTO> getExhibit(@PathVariable Long id){
        return ResponseEntity.ok(exhibitService.getExhibit(id));
    }

    @GetMapping
    public ResponseEntity<Page<ExhibitDTO>> getAllExhibits(@PageableDefault(size = 10) Pageable pageable, @RequestParam(required = false) String search, @RequestParam(required = false) String type
    ){
        return ResponseEntity.ok(exhibitService.getAllExhibits(pageable, search, type));
    }
}
