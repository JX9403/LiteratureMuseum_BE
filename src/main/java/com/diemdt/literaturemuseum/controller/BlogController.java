package com.diemdt.literaturemuseum.controller;

import com.diemdt.literaturemuseum.dto.BlogDTO;
import com.diemdt.literaturemuseum.service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blogs")
@RequiredArgsConstructor
public class BlogController {
    private final BlogService blogService;

    @PostMapping()

    public ResponseEntity<BlogDTO> createBlog (@RequestBody @Valid BlogDTO blogDTO){
        return ResponseEntity.ok(blogService.createBlog(blogDTO));
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BlogDTO> updateBlog(@PathVariable Long id,
                                                      @RequestBody @Valid BlogDTO blogDTO){
        return ResponseEntity.ok(blogService.updateBlog(id, blogDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteBlog (@PathVariable Long id ){
        blogService.deleteBlog(id);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogDTO> getBlog(@PathVariable Long id){
        return ResponseEntity.ok(blogService.getBlog(id));
    }

    @GetMapping
    public ResponseEntity<Page<BlogDTO>> getAllBlogs(@PageableDefault(size = 10) Pageable pageable, @RequestParam(required = false) String search,@RequestParam(required = false) Long userId ){
        return ResponseEntity.ok(blogService.getAllBlogs(pageable, search, userId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<BlogDTO>> getAllBlogsByAward(@PageableDefault(size = 10) Pageable pageable, @PathVariable String userId){
        return ResponseEntity.ok(blogService.getAllBlogsByUser(pageable, userId));
    }
}
