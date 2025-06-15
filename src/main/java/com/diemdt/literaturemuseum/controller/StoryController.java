package com.diemdt.literaturemuseum.controller;

import com.diemdt.literaturemuseum.dto.StoryDTO;
import com.diemdt.literaturemuseum.dto.WorkDTO;
import com.diemdt.literaturemuseum.service.StoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stories")
@RequiredArgsConstructor
public class StoryController {
    private final StoryService storyService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StoryDTO> createStory (@RequestBody @Valid StoryDTO storyDTO){
        return ResponseEntity.ok(storyService.createStory(storyDTO));
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StoryDTO> updateStory(@PathVariable Long id,
                                                      @RequestBody @Valid StoryDTO storyDTO){
        return ResponseEntity.ok(storyService.updateStory(id, storyDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteStory (@PathVariable Long id ){
        storyService.deleteStory(id);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StoryDTO> getStory(@PathVariable Long id){
        return ResponseEntity.ok(storyService.getStory(id));
    }

    @GetMapping
    public ResponseEntity<Page<StoryDTO>> getAllStories(@PageableDefault(size = 10) Pageable pageable, @RequestParam(required = false) String search, @RequestParam(required = false) Long authorId){
        return ResponseEntity.ok(storyService.getAllStories(pageable, search, authorId));
    }

    @GetMapping("/author/{authorId}")
    public ResponseEntity<Page<StoryDTO>> getAllStoriesByAuthor(@PageableDefault(size = 10) Pageable pageable, @RequestParam(required = false) String authorId){
        return ResponseEntity.ok(storyService.getAllStoriesByAuthor(pageable, authorId));
    }
}
