package com.diemdt.literaturemuseum.controller;

import com.diemdt.literaturemuseum.dto.ArtifactDTO;
import com.diemdt.literaturemuseum.service.ArtifactService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/artifacts")
@RequiredArgsConstructor
public class ArtifactController {
    private final ArtifactService artifactService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ArtifactDTO> createArtifact (@RequestBody @Valid ArtifactDTO artifactDTO){
        return ResponseEntity.ok(artifactService.createArtifact(artifactDTO));
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ArtifactDTO> updateArtifact(@PathVariable Long id,
                                                      @RequestBody @Valid ArtifactDTO artifactDTO){
        return ResponseEntity.ok(artifactService.updateArtifact(id, artifactDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteArtifact (@PathVariable Long id ){
        artifactService.deleteArtifact(id);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtifactDTO> getArtifact(@PathVariable Long id){
        return ResponseEntity.ok(artifactService.getArtifact(id));
    }

    @GetMapping
    public ResponseEntity<Page<ArtifactDTO>> getAllArtifacts(@PageableDefault(size = 10) Pageable pageable, @RequestParam(required = false) String search){
        return ResponseEntity.ok(artifactService.getAllArtifacts(pageable, search));
    }
}
