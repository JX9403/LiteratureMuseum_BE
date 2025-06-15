package com.diemdt.literaturemuseum.service;

import com.diemdt.literaturemuseum.dto.NewsDTO;
import com.diemdt.literaturemuseum.dto.NewsDTO;
import com.diemdt.literaturemuseum.dto.NewsDTO;
import com.diemdt.literaturemuseum.dto.NewsDTO;
import com.diemdt.literaturemuseum.entity.File;
import com.diemdt.literaturemuseum.entity.News;
import com.diemdt.literaturemuseum.entity.News;
import com.diemdt.literaturemuseum.exception.ResourceNotFoundException;
import com.diemdt.literaturemuseum.mapper.NewsMapper;
import com.diemdt.literaturemuseum.repository.FileRepo;
import com.diemdt.literaturemuseum.repository.NewsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final FileRepo fileRepo;

    @Transactional
    public NewsDTO createNews ( NewsDTO newsDTO ){
        News news = newsMapper.toEntity(newsDTO);
        news.setView(0);
        News savedNews = newsRepository.save(news);
        return newsMapper.toDTO(savedNews);
    }

    @Transactional
    public NewsDTO updateNews ( Long id , NewsDTO newsDTO) {
        News existingNews = newsRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("News not found"));
        existingNews.setContent(newsDTO.getContent());
        existingNews.setName(newsDTO.getName());

        News updatedNews = newsRepository.save(existingNews);

        return newsMapper.toDTO(updatedNews);
    }

    @Transactional
    public void deleteNews (Long id){
        if(!newsRepository.existsById(id)){
            throw new ResourceNotFoundException("News not found");
        }

        newsRepository.deleteById(id);
    }

    @Transactional
    public NewsDTO getNews(Long id) {
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("News not found"));

        news.setView(news.getView() + 1);
        NewsDTO dto = newsMapper.toDTO(news);

        List<File> files = fileRepo.findByTargetTypeAndTargetId(File.Type.NEWS, news.getId());
        dto.setFiles(files);

        return dto;
    }

    public Page<NewsDTO> getAllNews (Pageable pageable, String search){
        Page<News> newss;
        if (search == null || search.isBlank()) {
            newss =  newsRepository.findAll(pageable);
        } else {
            newss = newsRepository.findByNameContainingIgnoreCase(pageable, search);
        }

        return newss.map(news -> {
            NewsDTO dto = newsMapper.toDTO(news);

            List<File> files = fileRepo.findByTargetTypeAndTargetId(File.Type.NEWS, news.getId());
            dto.setFiles(files);

            return dto;
        });
    }
}
