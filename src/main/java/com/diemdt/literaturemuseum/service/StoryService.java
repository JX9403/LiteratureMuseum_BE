package com.diemdt.literaturemuseum.service;

import com.diemdt.literaturemuseum.dto.*;
import com.diemdt.literaturemuseum.dto.StoryDTO;
import com.diemdt.literaturemuseum.dto.StoryDTO;
import com.diemdt.literaturemuseum.dto.StoryDTO;
import com.diemdt.literaturemuseum.entity.*;
import com.diemdt.literaturemuseum.entity.Story;
import com.diemdt.literaturemuseum.exception.ResourceNotFoundException;
import com.diemdt.literaturemuseum.mapper.StoryMapper;
import com.diemdt.literaturemuseum.repository.AuthorRepository;
import com.diemdt.literaturemuseum.repository.FileRepo;
import com.diemdt.literaturemuseum.repository.StoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class StoryService {
    private final StoryRepository storyRepository;
    private final AuthorRepository authorRepository;
    private final StoryMapper storyMapper;
    private final FileRepo fileRepo;

    @Transactional
    public StoryDTO createStory ( StoryDTO storyDTO ){
        Story story = storyMapper.toEntity(storyDTO);
        story.setView(0);
        Author author = authorRepository.findById(storyDTO.getAuthorId())
                .orElseThrow(()-> new ResourceNotFoundException("Author not found"));
        story.setAuthor(author);

        Story savedStory = storyRepository.save(story);
        return storyMapper.toDTO(savedStory);
    }

    @Transactional
    public StoryDTO updateStory ( Long id , StoryDTO storyDTO) {
        Story existingStory = storyRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Story not found"));
        existingStory.setContent(storyDTO.getContent());
        existingStory.setName(storyDTO.getName());
        Author author = authorRepository.findById(storyDTO.getAuthorId())
                .orElseThrow(()-> new ResourceNotFoundException("Author not found"));
        existingStory.setAuthor(author);

        Story updatedStory = storyRepository.save(existingStory);

        return storyMapper.toDTO(updatedStory);
    }

    @Transactional
    public void deleteStory (Long id){
        if(!storyRepository.existsById(id)){
            throw new ResourceNotFoundException("Story not found");
        }

        storyRepository.deleteById(id);
    }

    @Transactional
    public StoryDTO getStory(Long id) {
        Story story = storyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Story not found"));

        story.setView(story.getView() + 1);
        StoryDTO dto = storyMapper.toDTO(story);

        List<File> files = fileRepo.findByTargetTypeAndTargetId(File.Type.STORY, story.getId());
        dto.setFiles(files);

        return dto;
    }


    public Page<StoryDTO> getAllStories(Pageable pageable, String search, Long authorId) {
        Page<Story> stories;

        boolean hasSearch = search != null && !search.isBlank();
        boolean hasAuthor = authorId != null;

        if (hasSearch && hasAuthor) {
            stories = storyRepository.findByNameContainingIgnoreCaseAndAuthorId(search, authorId, pageable);
        } else if (hasSearch) {
            stories = storyRepository.findByNameContainingIgnoreCase(search, pageable);
        } else if (hasAuthor) {
            stories = storyRepository.findByAuthorId(authorId, pageable);
        } else {
            stories = storyRepository.findAll(pageable);
        }

        return stories.map(story -> {
            StoryDTO dto = storyMapper.toDTO(story);
            List<File> files = fileRepo.findByTargetTypeAndTargetId(File.Type.STORY, story.getId());
            dto.setFiles(files);
            return dto;
        });
    }

    public Page<StoryDTO> getAllStoriesByAuthor (Pageable pageable, String authorId){

        Page<Story> stories;

        if (authorId == null ||authorId.isEmpty()  ) {
            throw new RuntimeException("Author id is required");
        } else {
            try {
                Long id = Long.parseLong(authorId);
                Author author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
                stories = storyRepository.findByAuthorId(id,pageable);
            } catch (NumberFormatException e) {
                throw new RuntimeException("Author ID must be a valid number");
            }
        }
        return stories.map(story -> {
            StoryDTO dto = storyMapper.toDTO(story);

            List<File> files = fileRepo.findByTargetTypeAndTargetId(File.Type.STORY, story.getId());
            dto.setFiles(files);

            return dto;
        });
    }
}
