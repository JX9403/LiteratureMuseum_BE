package com.diemdt.literaturemuseum.controller;

import com.diemdt.literaturemuseum.dto.AwardDTO;
import com.diemdt.literaturemuseum.service.AwardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/awards")
@RequiredArgsConstructor
public class AwardController {
    private final AwardService awardService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AwardDTO> createAward (@RequestBody @Valid AwardDTO awardDTO){
        return ResponseEntity.ok(awardService.createAward(awardDTO));
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AwardDTO> updateAward(@PathVariable Long id,
                                                      @RequestBody @Valid AwardDTO awardDTO){
        return ResponseEntity.ok(awardService.updateAward(id, awardDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAward (@PathVariable Long id ){
        awardService.deleteAward(id);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AwardDTO> getAward(@PathVariable Long id){
        return ResponseEntity.ok(awardService.getAward(id));
    }

    @GetMapping
    public ResponseEntity<Page<AwardDTO>> getAllAwards(@PageableDefault(size = 10) Pageable pageable, @RequestParam(required = false) String search){
        return ResponseEntity.ok(awardService.getAllAwards(pageable, search));
    }
}
