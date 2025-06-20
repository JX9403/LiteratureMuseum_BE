package com.diemdt.literaturemuseum.controller;

import com.diemdt.literaturemuseum.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final ArtifactRepository artifactRepo;
    private final AuthorRepository authorRepo;
    private final AwardRepository awardRepo;
    private final BlogRepository blogRepo;
    private final ExhibitRepository exhibitRepo;
    private final NewsRepository newsRepo;
    private final StoryRepository storyRepo;
    private final UserRepository userRepo;
    private final WorkRepository workRepo;
    private final CommentRepository commentRepo;

    @GetMapping("/counts")
    public ResponseEntity<Map<String, Long>> getEntityCounts() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("Hiện vật", artifactRepo.count());
        counts.put("Tác giả", authorRepo.count());
        counts.put("Tác phẩm", awardRepo.count());
        counts.put("Thảo luận", blogRepo.count());

        counts.put("Trưng bày", exhibitRepo.count());

        counts.put("Tin tức", newsRepo.count());
        counts.put("Câu chuyện", storyRepo.count());
        counts.put("Người dùng", userRepo.count());
        counts.put("Tác phẩm", workRepo.count());
        return ResponseEntity.ok(counts);
    }

    @GetMapping("/highlights")
    public ResponseEntity<Map<String, Object>> getHighlights() {
        Map<String, Object> result = new HashMap<>();

        // 1. Tin tức có lượt xem nhiều nhất
        result.put("topNews", newsRepo.findTopByOrderByViewDesc());

        // 2. Blog có lượt xem nhiều nhất
        result.put("topBlog", blogRepo.findTopByOrderByViewDesc());

        // 3. Story có lượt xem nhiều nhất
        result.put("topStory", storyRepo.findTopByOrderByViewDesc());

        // 4. User đăng nhiều blog nhất
        result.put("topBlogger", blogRepo.findTopUserByBlogCount());

        // 5. User có nhiều bình luận nhất
        result.put("topCommenter", commentRepo.findTopUserByCommentCount());

        return ResponseEntity.ok(result);
    }

}
