package com.diemdt.literaturemuseum.controller;

import com.diemdt.literaturemuseum.dto.AuthorDTO;
import com.diemdt.literaturemuseum.service.AuthorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthorDTO> createAuthor (@RequestBody @Valid AuthorDTO authorDTO){
        return ResponseEntity.ok(authorService.createAuthor(authorDTO));
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable Long id,
                                                      @RequestBody @Valid AuthorDTO authorDTO){
        return ResponseEntity.ok(authorService.updateAuthor(id, authorDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAuthor (@PathVariable Long id ){
        authorService.deleteAuthor(id);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorDTO> getAuthor(@PathVariable Long id){
        return ResponseEntity.ok(authorService.getAuthor(id));
    }

    @GetMapping
    public ResponseEntity<Page<AuthorDTO>> getAllAuthors(@PageableDefault(size = 10) Pageable pageable, @RequestParam(required = false) String search){
        return ResponseEntity.ok(authorService.getAllAuthors(pageable, search));
    }

    @GetMapping("/award/{awardId}")
    public ResponseEntity<Page<AuthorDTO>> getAllAuthorsByAward(@PageableDefault(size = 10) Pageable pageable, @PathVariable String awardId){
        return ResponseEntity.ok(authorService.getAllAuthorsByAward(pageable, awardId));
    }
}
