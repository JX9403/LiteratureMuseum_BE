package com.diemdt.literaturemuseum.controller;

import com.diemdt.literaturemuseum.dto.NewsDTO;
import com.diemdt.literaturemuseum.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NewsDTO> createNews (@RequestBody @Valid NewsDTO newsDTO){
        return ResponseEntity.ok(newsService.createNews(newsDTO));
    }

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NewsDTO> updateNews(@PathVariable Long id,
                                                      @RequestBody @Valid NewsDTO newsDTO){
        return ResponseEntity.ok(newsService.updateNews(id, newsDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteNews (@PathVariable Long id ){
        newsService.deleteNews(id);
        return  ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NewsDTO> getNews(@PathVariable Long id){
        return ResponseEntity.ok(newsService.getNews(id));
    }

    @GetMapping
    public ResponseEntity<Page<NewsDTO>> getAllNews(@PageableDefault(size = 10) Pageable pageable, @RequestParam(required = false) String search){
        return ResponseEntity.ok(newsService.getAllNews(pageable, search));
    }
}
